//Orders JS

$(document).ready(function () {
    // Retrieve orders from sessionStorage
    const orders = JSON.parse(sessionStorage.getItem("orders")) || [];

    // Populate the Orders table
    const $tableBody = $("#ordersTableBody");
    if (orders.length === 0) {
        $tableBody.append(`<tr><td colspan="4" class="text-center">No orders found</td></tr>`);
    } else {
        orders.forEach(order => {
            const row = `
                <tr>
                    <td>${order.id}</td>
                    <td>${order.name}</td>
                    <td>${order.quantity}</td>
                    <td>${order.timestamp}</td>
                </tr>
            `;
            $tableBody.append(row);
        });
    }

    // Handle Logout functionality
    // Populate modal with user details when the logout button is clicked
    $('#logoutButton').click(function (e) {
        e.preventDefault();  // Prevent default action for the logout link
        const loginName = 'John Doe';
        const loginEmail = 'john.doe@example.com';
        $('#modalUserName').text(loginName); // Populate modal with user name
        $('#modalUserEmail').text(loginEmail); // Populate modal with user email
 
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
        window.location.href = '../../Admin/Login/login.html'; // Replace with your actual login page
    });
});

 
 
 
 