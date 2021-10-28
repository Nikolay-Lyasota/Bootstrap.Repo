document.addEventListener('DOMContentLoaded', function () {

    let refreshButton = document.getElementById('refresh')
    let addUserButton = document.getElementById('addUserButton')
    let prepareToDeleteButton = document.getElementById('deleteBtn')
    let removeButton = document.getElementById('deleteButton')

    removeButton.addEventListener('click', function() {
        removeUser()
    })


    addUserButton.addEventListener('click', function (event) {
        event.preventDefault()
        commitNewUser()
    })

    refreshButton.addEventListener('click', function (event) {
        event.preventDefault()
        prepareToDel()
        jRefreshTableElement()

    })

    prepareToDeleteButton.addEventListener('click', function (event) {
        // event.preventDefault()
        prepareToDel()

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


    })
})

function prepareToDel() {
    let table = document.getElementById('refreshableTable')
    let buttons = table.getElementsByTagName('tr')
    for (let i = 0, len = buttons.length; i < len; i++) {
        buttons[i].onclick = function () {
            console.log('clicked')
            let id = parseInt(this.innerText.split("")[0])
            fetch('http://localhost:8080/get/' + id)
                .then((response) => {
                    response.json()
                        .then((deletedUserJson) => {
                            document.getElementById('idUserDelete').value = deletedUserJson.id
                            document.getElementById('usernameDelete').value= deletedUserJson.name
                            document.getElementById('ageDelete').value = deletedUserJson.age
                            document.getElementById('emailDelete').value = deletedUserJson.email
                        })
                })
        }
    }
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

function removeListener() {

}


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

function commitNewUser() {
    let newUser = createUser()
    fetch('http://localhost:8080/create', {
        method: 'POST',
        body: newUser,
        headers: {
            'Content-Type': 'application/json',
        },
    })
        .then(response => {
            console.log(response)
        })
        .then(() => jRefreshTableElement())
        .catch(err => console.log(err))

}


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

function jRefreshTableElement() {
    $('#refreshableTable').load('http://localhost:8080/admin #refreshableTable')
    console.log("table reloaded")
}


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


