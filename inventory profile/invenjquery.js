$(document).ready(function () {
    // Simulating login data (Replace these with actual values from your login page)
    const loginName = "John Doe"; // Assume this comes from the login form
    const loginEmail = "john.doe@example.com"; // Assume this comes from the login form
    const loginAddress = "9876 Elm Street, Springfield"; // Assume this comes from the login form

    // Populate the profile fields with the data from login
    $('#name').val(loginName);
    $('#email').val(loginEmail);
    $('#address').val(loginAddress);

    // Handle Edit/Save functionality
    let isEditing = false;

    function toggleEditSave() {
        if (isEditing) {
            $('input').attr('readonly', true);
            $('.btn-warning').text('Edit Profile');
            isEditing = false;

            // Optionally, log or process updated data here
            const updatedName = $('#name').val();
            const updatedEmail = $('#email').val();
            const updatedAddress = $('#address').val();
            console.log("Updated Data:", updatedName, updatedEmail, updatedAddress);
        } else {
            $('input').attr('readonly', false);
            $('.btn-warning').text('Save Profile'); 
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
});
