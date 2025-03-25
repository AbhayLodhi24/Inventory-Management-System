var suppliers = [
    { id: 1, name: "AGC", email: "agc@gmail.com", phone: "88827837778", address: "Main Street" },
    { id: 2, name: "B Group", email: "info@bgroup.com", phone: "778777387", address: "Main Street" },
    { id: 3, name: "HP Group", email: "info@hpgroup.com", phone: "333333343", address: "Main Street" }
];

function populateTable() {
    var tableBody = $('#supplierTable');
    tableBody.empty();
    suppliers.forEach(function (supplier) {
        tableBody.append(
            `<tr>
                <td>${supplier.id}</td>
                <td>${supplier.name}</td>
                <td>${supplier.email}</td>
                <td>${supplier.phone}</td>
                <td>${supplier.address}</td>
                <td>
                    <button class="btn btn-edit btn-sm" onclick="editSupplier(${supplier.id})">Edit</button>
                    <button class="btn btn-delete btn-sm" onclick="deleteSupplier(${supplier.id})">Delete</button>
                </td>
            </tr>`
        );
    });
}

function editSupplier(id) {
    var supplier = suppliers.find(supplier => supplier.id === id);
    if (supplier) {
        $('#supplierID').val(supplier.id);
        $('#supplierName').val(supplier.name);
        $('#supplierEmail').val(supplier.email);
        $('#supplierPhone').val(supplier.phone);
        $('#supplierAddress').val(supplier.address);
        $('#supplierModalLabel').text("Edit Supplier");
        $('#modalSubmitBtn').text("Save changes");
        $('#supplierModal').modal('show');
    }
}

function deleteSupplier(id) {
    suppliers = suppliers.filter(supplier => supplier.id !== id);
    populateTable();
}

$('#supplierForm').on('submit', function (e) {
    e.preventDefault();
    var id = $('#supplierID').val();
    var name = $('#supplierName').val();
    var email = $('#supplierEmail').val();
    var phone = $('#supplierPhone').val();
    var address = $('#supplierAddress').val();

    if (id) {
        // Edit existing supplier
        var supplier = suppliers.find(supplier => supplier.id == id);
        supplier.name = name;
        supplier.email = email;
        supplier.phone = phone;
        supplier.address = address;
    } else {
        // Add new supplier
        var newID = suppliers.length ? Math.max(...suppliers.map(s => s.id)) + 1 : 1;
        suppliers.push({
            id: newID,
            name: name,
            email: email,
            phone: phone,
            address: address
        });
    }
    $('#supplierModal').modal('hide');
    populateTable();
});

// Add Supplier button click event
$('#addSupplierBtn').click(function () {
    // Clear the form and switch to Add mode
    $('#supplierID').val('');
    $('#supplierName').val('');
    $('#supplierEmail').val('');
    $('#supplierPhone').val('');
    $('#supplierAddress').val('');
    $('#supplierModalLabel').text("Add Supplier");
    $('#modalSubmitBtn').text("Add Supplier");
    $('#supplierModal').modal('show');
});

$('#search').on('input', function () {
    var searchText = $(this).val().toLowerCase();
    var filteredSuppliers = suppliers.filter(supplier =>
        supplier.name.toLowerCase().includes(searchText) ||
        supplier.email.toLowerCase().includes(searchText)
    );
    var tableBody = $('#supplierTable');
    tableBody.empty();
    filteredSuppliers.forEach(function (supplier) {
        tableBody.append(
            `<tr>
                <td>${supplier.id}</td>
                <td>${supplier.name}</td>
                <td>${supplier.email}</td>
                <td>${supplier.phone}</td>
                <td>${supplier.address}</td>
                <td>
                    <button class="btn btn-edit btn-sm" onclick="editSupplier(${supplier.id})">Edit</button>
                    <button class="btn btn-delete btn-sm" onclick="deleteSupplier(${supplier.id})">Delete</button>
                </td>
            </tr>`
        );
    });
});

// Initial population of the supplier table
$(document).ready(function () {
    populateTable();
});

 // Handle Logout functionality
    // Populate modal with user details when the logout button is clicked
    $('#logoutButton').click(function (e) {
        e.preventDefault(); // Prevent default action for the logout link
      //  $('#modalUserName').text(loginName); // Populate modal with user name
      //  $('#modalUserEmail').text(loginEmail); // Populate modal with user email
 
        // Show the logout confirmation modal
        $('#logoutModal').modal('show');
    });
 
    // Cancel Logout Functionality
    $('#cancelLogout').click(function () {
        $('#logoutModal').modal('hide'); // Simply hide the modal without any further action
    });
 
    // Handle "Logout" confirmation in the modal
    $('#confirmLogout').click(function () {
        $('#logoutModal').modal('hide'); // Hide the modal
        console.log("User has logged out."); // Log the action
         // Notify the user (can be replaced with actual logout logic)
        // Redirect to the login page or perform other logout actions
        window.location.href = '../Login/login.html'; // Replace with your actual login page
    });
 