const url = 'http://localhost:8080/admin/users/';
const urlRoles = 'http://localhost:8080/admin/roles/';
const container = document.querySelector('.usersTbody');
const newUserForm = document.getElementById('newUserForm');
const editUserForm = document.getElementById('editUserForm');
const deleteUserForm = document.getElementById('deleteUserForm');
const btnCreate = document.getElementById('new-user-tab');
const newRoles = document.getElementById('newRoles');
let result = '';

var editUserModal = new bootstrap.Modal(document.getElementById('editUserModal'));
var deleteUserModal = new bootstrap.Modal(document.getElementById('deleteUserModal'));
const editId = document.getElementById('editId');
const editFirstName = document.getElementById('editFirstName');
const editLastName = document.getElementById('editLastName');
const editEmail = document.getElementById('editEmail');
const editUsername = document.getElementById('editUsername');
const editPassword = document.getElementById('editPassword');
const editRoles = document.getElementById('editRoles');

const delId = document.getElementById('delId');
const delFirstName = document.getElementById('delFirstName');
const delLastName = document.getElementById('delLastName');
const delEmail = document.getElementById('delEmail');
const delUsername = document.getElementById('delUsername');
const delRoles = document.getElementById('delRoles');

const newFirstName = document.getElementById('newFirstName');
const newLastName = document.getElementById('newLastName');
const newEmail = document.getElementById('newEmail');
const newUsername = document.getElementById('newUsername');
const newPassword = document.getElementById('newPassword');

let rolesArr = [];

const getAllUsers = (users) => {
    users.forEach(user => {
        let roles = '';
        user.roles.forEach(
            role => {
                r = role.name.substring(5);
                roles += r + ' ';
            }
        );
        result += `
            <tr>
                <td>${user.id}</td>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>                
                <td>${user.email}</td>
                <td>${user.username}</td>
                <td>
                 ${roles}
                </td>
                <td><a class="btnEdit btn btn-sm btn-info text-white">Edit</a></td>
                <td><a class="btnDelete btn btn-danger btn-sm">Delete</a></td>
            </tr>
            `
    })
    container.innerHTML = result;
}

const getRoles = (roles) => {
    rolesOptions = '';
    roles.forEach(role => {
        rolesOptions += `
            <option value = ${role.id}>${role.name.substring(5)}</option>
            `
        rolesArr.push(role);
    })
    newRoles.innerHTML = rolesOptions;
    editRoles.innerHTML = rolesOptions;
    delRoles.innerHTML = rolesOptions;
}


fetch(url)
    .then(res => res.json())
    .then(data => getAllUsers(data))
    .catch(error => console.log(error));

var allRoles;

fetch(urlRoles)
    .then(res => res.json())
    .then(data => {
        allRoles = data;
        getRoles(allRoles)
    });


const refreshListOfUsers = () => {
    fetch(url)
        .then(res => res.json())
        .then(data => {
            result = '';
            getAllUsers(data)
        })
}

const on = (element, event, selector, handler) => {
    element.addEventListener(event, e => {
        if (e.target.closest(selector)) {
            handler(e)
        }
    })
}



on(document, 'click', '.btnDelete', e => {
    const row = e.target.parentNode.parentNode;
    idForm = row.children[0].innerHTML;
    const firstNameForm = row.children[1].innerHTML;
    const lastNameForm = row.children[2].innerHTML;
    const emailForm = row.children[3].innerHTML;
    const usernameForm = row.children[4].innerHTML;

    delId.value = idForm;
    delFirstName.value = firstNameForm;
    delLastName.value = lastNameForm;
    delEmail.value = emailForm;
    delUsername.value = usernameForm;
    deleteUserModal.show();
})



let idForm = 0;
on(document, 'click', '.btnEdit', e => {
    const row = e.target.parentNode.parentNode;
    idForm = row.children[0].innerHTML;
    const firstNameForm = row.children[1].innerHTML;
    const lastNameForm = row.children[2].innerHTML;
    const emailForm = row.children[3].innerHTML;
    const usernameForm = row.children[4].innerHTML;

    editId.value = idForm;
    editFirstName.value = firstNameForm;
    editLastName.value = lastNameForm;
    editEmail.value = emailForm;
    editUsername.value = usernameForm;
    editPassword.value = ''
    editRoles.options.selectedIndex = -1;
    editUserModal.show();

})



btnCreate.addEventListener('click', () => {
    newFirstName.value = ''
    newLastName.value = '';
    newUsername.value = '';
    newEmail.value = '';
    newPassword.value = ''
    newRoles.options.selectedIndex = -1;
});




deleteUserForm.addEventListener('submit', (e) => {
    e.preventDefault();
    fetch(url + delId.value, {
        method: 'DELETE',
        headers: {
            'Content-type': 'application/json; charset=UTF-8'
        },
    })
        .then(res => res.json())
        .catch(err => console.log(err))
        .then(refreshListOfUsers);
    deleteUserModal.hide();
});



newUserForm.addEventListener('submit', (e) => {
    let rolesJ = [];
    e.preventDefault();
    const selectedOpts = [...newRoles.options]
        .filter(x => x.selected)
        .map(x => x.value);

    selectedOpts.forEach(
        role => {
            rolesJ.push(rolesArr[role - 1])
        }
    );

    const fetchFunction = async () => {
        const fetchedData = await
            fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    firstName: newFirstName.value,
                    lastName: newLastName.value,
                    email: newEmail.value,
                    username: newUsername.value,
                    password: newPassword.value,
                    roles: rolesJ
                })
            });

        if (!fetchedData.ok) {
            fetchedData.json()
                .then(data => alert(data.message))
        }
        return fetchedData;
    }

    fetchFunction()
        .then(response => response.json())
        .catch(err => console.log(err))
        .then(refreshListOfUsers);
    const navtab1 = document.getElementById('all-users-tab');
    const navtab2 = document.getElementById('new-user-tab');
    const tab1 = document.getElementById('all-users');
    const tab2 = document.getElementById('new-user');

    navtab1.setAttribute("class", "nav-link active");
    navtab2.setAttribute("class", "nav-link");
    tab1.setAttribute("class", "tab-pane fade active show");
    tab2.setAttribute("class", "tab-pane fade");

})

editUserForm.addEventListener('submit', (e) => {
    let rolesJ = [];
    e.preventDefault();
    const selectedOpts = [...editRoles.options]
        .filter(x => x.selected)
        .map(x => x.value);

    selectedOpts.forEach(
        role => {
            rolesJ.push(rolesArr[role - 1])
        }
    );

    const fetchFunction = async () => {
        const fetchedData = await fetch(url + idForm, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id: editId.value,
                firstName: editFirstName.value,
                lastName: editLastName.value,
                email: editEmail.value,
                username: editUsername.value,
                password: editPassword.value,
                roles: rolesJ
            })
        });

        if (!fetchedData.ok) {
            fetchedData.json()
                .then(data => alert(data.message))
        }
        return fetchedData;
    }
    fetchFunction()
        .then(response => response.json)
        .then(refreshListOfUsers)
    editUserModal.hide();
})

const firstTabPill = document.querySelector('.ddpills');
const firstTab = document.querySelector('.ddtabs');

window.onload = function (){
    firstTabPill.className = 'ddpills nav-link text-start active';
    firstTab.className = 'ddtabs tab-pane fade active show';
}