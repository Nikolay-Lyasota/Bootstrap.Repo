document.addEventListener('DOMContentLoaded', function () {
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

    editButton.addEventListener('click', function (event) {
        getUserToUpdate()
    })

    removeButton.addEventListener('click', function () {
        removeUser()
    })

    addUserButton.addEventListener('click', function (event) {
        event.preventDefault()
        commitNewUser()
    })

    prepareToDeleteButton.addEventListener('click', function () {
        getUserToDelete()
    })
})

function jRefreshTableElement() {
   $('#refreshableTable')
        .load('http://localhost:8080/admin #refreshableTable')
    console.log("table reloaded")
    setTimeout(() => getUserToDelete(), 1000)
    setTimeout(() => getUserToUpdate(), 1000)
}


function getUserToDelete() {
    let selector = document.getElementsByClassName('btn btn-danger delete')
    for (let i = 0; i < selector.length; i++) {
        selector[i].addEventListener('click', () => {
            let number = parseInt(selector[i].parentElement.parentElement.innerText.split('')[0])
            console.log(number + ' id to delete')
            fetch('http://localhost:8080/get/' + number)
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
            let number = parseInt(selector[i].parentElement.parentElement.innerText.split('')[0])
            console.log(number + ' id to edit')
            fetch('http://localhost:8080/get/' + number)
                .then((response) => {
                    response.json()
                        .then((updatedUserJson) => {
                            document.getElementById('idUserEdit').value = updatedUserJson.id
                            document.getElementById('nameEdit').value = updatedUserJson.name
                            document.getElementById('ageEdit').value = updatedUserJson.age
                            document.getElementById('emailEdit').value = updatedUserJson.email
                            document.getElementById('passwordEdit').value = updatedUserJson.password
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
    fetch('http://localhost:8080/patch', {
        method: 'POST',
        body: patchedUser,
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            console.log('err' + response)
        })
        .then(() => console.log('user update'))
        .then(() => jRefreshTableElement())
        .catch(err => console.log(err))
}


function commitNewUser() {
    let newUser = createUser()
    fetch('http://localhost:8080/create', {
        method: 'POST',
        body: newUser,
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            console.log(response)
        })
        .then(() => jRefreshTableElement())
        .then(() => $('.nav-tabs a:first').tab('show'))
        .then(() => console.log('redirected'))
        .catch(err => console.log(err))
}

function removeUser() {
    let id = document.getElementById('idUserDelete').value
    fetch('http://localhost:8080/delete/' + id, {
        method: 'DELETE'
    })
        .then(() => {
            console.log('user with id: ' + id + ' deleted')
        })
        .then(() => jRefreshTableElement())
}



// function onClickElem() {
//    document.addEventListener('click', function (elem) {
//        console.log(elem)
//    })
// }

// function prepareToDel() {
//     let table = document.getElementById('refreshableTable')
//     let buttons = table.getElementsByTagName('tr')
//     let result
//     for (let i = 0, len = buttons.length; i < len; i++) {
//         buttons[i].onclick = function() {
//             console.log('clicked ')
//             console.log(this)
//             result = (parseInt(this.innerText.split("")[0]))
//             return Number(parseInt(this.innerText.split("")[0]))
//         }
//     }
// }
//
// function fillDeletableUser() {
//     let id = prepareToDel()
//     console.log('value ' + id)
//     fetch('http://localhost:8080/get/' + id)
//         .then((response) => {
//             response.json()
//                 .then((deletedUserJson) => {
//                     document.getElementById('idUserDelete').value = deletedUserJson.id
//                     document.getElementById('usernameDelete').value= deletedUserJson.name
//                     document.getElementById('ageDelete').value = deletedUserJson.age
//                     document.getElementById('emailDelete').value = deletedUserJson.email
//                 })
//         })
// }

// function prepareToDel() {
//     let table = document.getElementById('refreshableTable')
//     let buttons = table.getElementsByTagName('tr')
//     for (let i = 0, len = buttons.length; i < len; i++) {
//         buttons[i].onclick = function () {
//             let id = parseInt(this.innerText.split("")[0])
//             console.log(id)
//             fetch('http://localhost:8080/get/' + id)
//                 .then((response) => {
//                     response.json()
//                         .then((deletedUserJson) => {
//                             document.getElementById('idUserDelete').value = deletedUserJson.id
//                             document.getElementById('usernameDelete').value= deletedUserJson.name
//                             document.getElementById('ageDelete').value = deletedUserJson.age
//                             document.getElementById('emailDelete').value = deletedUserJson.email
//                         })
//                 })
//         }
//     }
// }



// function readyToRefresh() {
//     let table = document.getElementById('refreshableTable');
//     fetch('http://localhost:8080/all')
//         .then((response) => {
//             response.json().then(function (data) {
//                 for(let i = 0, len = data.lenght; i < len; i++) {
//                     let elemTr = document.createElement('tr');
//                     let datum = data[i];
//                     let elemTd = document.createElement('td');
//                     elemTd.innerHTML = datum.id
//                     elemTr.appendChild(elemTd)
//                     elemTd = document.createElement('td')
//                     elemTd.innerHTML = datum.name
//                     elemTr.appendChild(elemTd)
//                     elemTd = document.createElement('td')
//                     elemTd.innerHTML = datum.age
//                     elemTr.appendChild(elemTd)
//                     elemTd = document.createElement('td')
//                     elemTd.innerHTML = datum.email
//                     elemTr.appendChild(elemTd)
//                     elemTd = document.createElement('td')
//                     elemTd.innerHTML = datum.roles
//                     elemTr.appendChild(elemTd)
//                     table.appendChild(elemTr)
//                 }
//             })
//         })
//
//
// }

// function getOnClick() {
//     let table = document.getElementById('refreshableTable')
//     let values = table.getElementsByTagName('tr')
//
//     for (let i = 0, len = values.length; i < len; i++) {
//         values[i].onclick = function () {
//             console.log(' innerText' + this.innerText)
//             let fullTr = this.innerText.split("")[0]
//             console.log(fullTr)
//             // console.log(this.parentElement.firstChild)
//         }
//     }
// }


// function getToDelete() {
//     let table = document.getElementById('refreshableTable')
//     let values = table.getElementsByTagName('td')
//
//     for(let i = 0, len = values.length; i < len ; i++ ) {
//         values[i].onclick = function () {
//
//             console.log(this.innerHTML)
//         }
//     }
//     console.log(id)
// }


// function updateTable(selector) {
//     try {
//         let loc = fetch('http://localhost:8080/admin').text()
//         let newTable = new DOMParser().parseFromString(loc, 'text/html')
//         document.querySelector(selector).outerHTML = newTable.querySelector(selector).outerHTML
//         console.log(newTable)
//         return true
//     } catch (err) {
//         console.log(err)
//         return false
//     }
// }
//
// function ref() {
//
//     fetch('http://localhost:8080/allusers').then((arg) => document.getElementById('refreshableTable').innerHTML = arg)
// }

// function refresher() {
//     let parser = new DOMParser()
//     let url = 'http://localhost:8080/all'
//     fetch(url)
//         .then((response) => response.text())
//         .then((response) => parser.parseFromString(response, 'text/html'))
//         .then((table) => {
//             (table).getElementById('refreshableTable').innerHTML
//         })
//         .catch(e => console.log(e))
// }


// updateTable('table table-striped#refreshableTable')

// addUserButton.addEventListener('click', function (event) {
//     createUser()
//     event.preventDefault()
//     alert('added')
// })

// addUserButton.addEventListener('click', function (event) {
//     event.preventDefault()
// })

// refreshButton.addEventListener('click',refreshUsersTable)


// addUserButton.addEventListener('click', createUser)


// try {
//     let table = document.getElementById('usersTable')
//     table.refresh()
// }
// catch (e) {
//     console.log(e)
//     alert(')=')
// }

// try {
//     document.querySelector('tab-pane active').addEventListener('click', event => {
//         document.getElementById('usersTable').contentWindow.location.reload(true);
//     });
//     alert('success create')
// }
// catch (e) {
//     // alert(e)
//     alert('err')
// }

// let buttons = document.getElementsByClassName('btn btn-danger delete')
// Array.from(buttons).forEach((currentButton) => currentButton.addEventListener('click', selectFunction))
//
//
// function selectFunction() {
//     let button = parseInt(prepareToDeleteButton.parentElement.parentElement.innerText.split('')[0])
//     console.log(button)
// }

// let table = document.getElementById('refreshableTable')
// let buttons = table.getElementsByTagName('tr')
//
// Array.from(buttons).forEach(but => but.addEventListener('click', selectFunction))
//
// function selectFunction() {
//     let button = parseInt(prepareToDeleteButton.parentElement.parentElement.innerText.split('')[0])
//     console.log(button)
// }


// getOnClick()


// let deletedId = parseInt(prepareToDeleteButton.parentElement.parentElement.innerText.split('')[0])
// console.log('Id for delete:' + deletedId)
// fetch('http://localhost:8080/get/' + deletedId)
//     .then((response) => {
//         document.getElementById('idUserDelete').innerHTML = response['id']
//         console.log('id ' + deletedId + ' selected')
//     })
// document.getElementById('del').hidden = false
