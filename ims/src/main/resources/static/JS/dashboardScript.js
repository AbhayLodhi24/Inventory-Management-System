$(document).ready(function () {
    // Sample Data (Can be replaced with API data)
    const stockData = [
        {
            title: "Out of Stock Products",
            content: "Screen (Electronic)"
        },
        {
            title: "Highest Sale Product",
            content: `
                Name: Monitor <br>
                Category: Electronic <br>
                Total Units Sold: 2
            `
        },
        {
            title: "Low Stock Products",
            content: `
                Monitor - 3 left (Electronic) <br>
                PC - 3 left (Tech) <br>
                RAM - 2 left (Tech)
            `
        }
    ];

    // Render Cards Dynamically
    stockData.forEach((item) => {
        $("#stock-info").append(`
            <div class="col-md-4">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">${item.title}</h5>
                        <p>${item.content}</p>
                    </div>
                </div>
            </div>
        `);
    });

    // Handle Logout functionality
    // Populate modal with user details when the logout button is clicked
    $('#logoutButton').click(function (e) {
        e.preventDefault(); // Prevent default action for the logout link
        const loginName = "admin"; // Example user name
        const loginEmail = "admin@gmail.com"; // Example user email
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
        // Redirect to the login page or perform other logout actions
        window.location.href = '../Login/login.html'; // Replace with your actual login page
    });
});
