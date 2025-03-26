$(document).ready(function () {
    // Simulated user data (replace with actual dynamic data from your login system)
    const loginName = "admin"; // Example user name
    const loginEmail = "admin@gmail.com"; // Example user email
    const loginAddress = "9876 Elm Street, Springfield"; // Example user address

    // Populate the profile fields with the data from login
    $('#name').val(loginName);
    $('#email').val(loginEmail);
    $('#address').val(loginAddress);

    // Handle Edit/Save functionality for profile fields
    let isEditing = false;

    function toggleEditSave() {
        if (isEditing) {
            $('input').attr('readonly', true); // Make fields readonly
            $('.btn-warning').text('Edit Profile'); // Change button text to "Edit"
            isEditing = false;

            // Optionally log or process updated data here
            const updatedName = $('#name').val();
            const updatedEmail = $('#email').val();
            const updatedAddress = $('#address').val();
            console.log("Updated Data:", updatedName, updatedEmail, updatedAddress);
        } else {
            $('input').attr('readonly', false); // Make fields editable
            $('.btn-warning').text('Save Profile'); // Change button text to "Save"
            isEditing = true;
        }
    }

    // Edit/Save button functionality
    $('.btn-warning').click(function () {
        toggleEditSave();
    });

    // Allow clicking on input fields to enable editing
    $('input').click(function () {
        if (!isEditing) {
            toggleEditSave();
        }
    });

    // Handle Logout functionality
    // Populate modal with user details when the logout button is clicked
    $('#logoutButton').click(function (e) {
        e.preventDefault(); // Prevent default action for the logout link
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
        console.log("User has logged out."); // Log the action
        // Redirect to the login page or perform other logout actions
        window.location.href = "../Login/login.html"; // Replace with your actual login page
    });
});
