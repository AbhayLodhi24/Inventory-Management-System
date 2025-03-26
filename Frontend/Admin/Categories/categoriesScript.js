$(document).ready(function () {
    // Array to store categories
    let categories = [
        { id: 1, name: "Tech", description: "Technology-related items" },
        { id: 2, name: "Fashion", description: "Fashionable clothing and accessories" },
        { id: 3, name: "Electronic", description: "Electronic gadgets and devices" }
    ];

    // Function to render table
    function renderTable() {
        let tbody = $("tbody");
        tbody.empty(); // Clear current table rows
        categories.forEach(category => {
            let row = `
            <tr data-id="${category.id}">
                <td>${category.id}</td>
                <td class="category-name">${category.name}</td>
                <td>
                    <button class="btn btn-edit btn-sm edit-btn">Edit</button>
                    <button class="btn btn-delete btn-sm delete-btn">Delete</button>
                </td>
            </tr>
        `;
            tbody.append(row);
        });
    }

    // Render the table on page load
    renderTable();

    // Edit button functionality
    $(document).on("click", ".edit-btn", function () {
        let row = $(this).closest("tr");
        let categoryId = row.data("id");
        let category = categories.find(cat => cat.id === categoryId);
        $("#category-name").val(category.name);
        $("#category-description").val(category.description);
        $("#edit-category-form").data("categoryId", categoryId);
        $('#categoryModal').modal('show');
    });

    // Save changes to the category
    $("#edit-category-form").submit(function (e) {
        e.preventDefault();
        let categoryId = $(this).data("categoryId");
        let newName = $("#category-name").val();
        let newDescription = $("#category-description").val();
        let category = categories.find(cat => cat.id === categoryId);
        if (category) {
            category.name = newName; // Update the name in the array
            category.description = newDescription; // Update the description in the array
            renderTable(); // Re-render the table
        }
        $('#categoryModal').modal('hide');
    });

    // Delete button functionality
    $(document).on("click", ".delete-btn", function () {
        let row = $(this).closest("tr");
        let categoryId = row.data("id");
        categories = categories.filter(cat => cat.id !== categoryId); // Remove from array
        renderTable(); // Re-render the table
    });

    // Cancel button functionality
    $("#cancel-btn").click(function () {
        $("#edit-category-form")[0].reset();
        $('#categoryModal').modal('hide');
    });

    // Search functionality
    $("#search-input").on('input', function () {
        var searchText = $('#search-input').val().toLowerCase(); 
        $("tbody tr").each(function() {                  
                    var rowName = $(this).find('td:eq(1)').text().toLowerCase();  
                    if ( searchText === '' || rowName.includes(searchText)) {  
                $(this).show();  
            } else {  
                $(this).hide();  
            }  
        });
    });

    
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
 