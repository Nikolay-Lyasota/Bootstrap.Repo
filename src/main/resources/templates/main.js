// document.addEventListener('DOMContentLoaded', async function (event) {
//     await event
//     setListeners()
// })

$(document).ready(function () {
    setListeners();
});

function setListeners() {
    getUserToUpdate()
    getUserToDelete()
    let addUserButton = document.getElementById('addUserButton')
    let prepareToDeleteButton = document.getElementById('deleteBtn')
    let removeButton = document.getElementById('deleteButton')
    let editButton = document.getElementById('editBtn')
    let commitEdit = document.getElementById('commitEdit')


    commitEdit.addEventListener('click', function () {
        commitUpdatedUser()
    })

    editButton.addEventListener('click', function () {
        getUserToUpdate()
    })

    removeButton.addEventListener('click', function (event) {
        event.preventDefault()
        removeUser()
    })

    addUserButton.addEventListener('click', function (event) {
        event.preventDefault()
        commitNewUser()
    })

    prepareToDeleteButton.addEventListener('click', function () {
        getUserToDelete()
    })
}

function jRefreshTableElement() {
    $('#refreshableTable').load('http://localhost:8080/admin #refreshableTable', function () {
        getUserToDelete()
        getUserToUpdate()
        console.log("table reloaded")
    })
}


function getUserToDelete() {
    let selector = document.getElementsByClassName('btn btn-danger delete')
    for (let i = 0; i < selector.length; i++) {
        selector[i].addEventListener('click', () => {
            let id = parseInt(selector[i].parentElement.parentElement.innerText.split('')[0])
            console.log(id + ' id to delete')
            fetch('/api/users/' + id)
                .then((response) => {
                    response.json()
                        .then((deletedUserJson) => {
                            document.getElementById('idUserDelete').value = deletedUserJson.id
                            document.getElementById('usernameDelete').value = deletedUserJson.name
                            document.getElementById('ageDelete').value = deletedUserJson.age
                            document.getElementById('emailDelete').value = deletedUserJson.email
                        })

                })
        })
    }
}

function getUserToUpdate() {
    let selector = document.getElementsByClassName('btn btn-info edit')
    for (let i = 0; i < selector.length; i++) {
        selector[i].addEventListener('click', () => {
            let id = parseInt(selector[i].parentElement.parentElement.innerText.split('')[0])
            console.log(id + ' id to edit')
            fetch('/api/users/' + id)
                .then((response) => {
                    response.json()
                        .then((updatingUserJson) => {
                            document.getElementById('idUserEdit').value = updatingUserJson.id
                            document.getElementById('nameEdit').value = updatingUserJson.name
                            document.getElementById('ageEdit').value = updatingUserJson.age
                            document.getElementById('emailEdit').value = updatingUserJson.email
                        })
                })
        })
    }
}

function createUser() {
    let rolesArray = []
    let select = document.getElementById('addRoles')
    for (let i = 0; i < select.options.length; i++) {
        if (select.options[i].selected === true) {
            rolesArray.push(select.options[i].value)
        }
    }

    let userDto = {
        "name": document.getElementById('addName').value,
        "age": document.getElementById('addAge').value,
        "email": document.getElementById('addEmail').value,
        "password": document.getElementById('addPassword').value,
        "roles": rolesArray
    }
    return JSON.stringify(userDto)
}

function updateUser() {
    let rolesArray = []
    let select = document.getElementById('editRoles')
    for (let i = 0; i < select.options.length; i++) {
        if (select.options[i].selected === true) {
            rolesArray.push(select.options[i].value)
        }
    }

    let updatableUser = {
        "id": Number(document.getElementById('idUserEdit').value),
        "name": document.getElementById('nameEdit').value,
        "age": document.getElementById('ageEdit').value,
        "email": document.getElementById('emailEdit').value,
        "password": document.getElementById('passwordEdit').value,
        "roles": rolesArray
    }
    return JSON.stringify(updatableUser)
}

function commitUpdatedUser() {
    let patchedUser = updateUser()
    fetch('/api/users/id', {
        method: 'PUT',
        body: patchedUser,
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .catch(err => console.log(err))
        .then(() => console.log('user update'))
        .then(() => jRefreshTableElement())
}


function commitNewUser() {
    let newUser = createUser()
    fetch('/api/users/create', {
        method: 'POST',
        body: newUser,
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .catch(err => console.log(err))
        .then(response => {
            console.log(response)
        })
        .then(() => jRefreshTableElement())
        .then(() => $('.nav-tabs a:first').tab('show'))
        .then(() => console.log('redirected'))
}

function removeUser() {
    let id = document.getElementById('idUserDelete').value
    fetch('/api/users/' + id, {
        method: 'DELETE'
    })
        .then(() => {
            console.log('user with id: ' + id + ' deleted')
        })
        .then(() => jRefreshTableElement())
}