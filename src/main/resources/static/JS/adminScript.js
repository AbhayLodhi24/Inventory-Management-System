$(document).ready(function () {
    const nameInput = $('#admin-name'); // Corresponds to username in DTO
    const emailInput = $('#admin-email');
    const addressInput = $('#admin-address');
    const editButton = $('#adminEditButton');
    const saveButton = $('#adminSaveButton');
    const cancelButton = $('#adminCancelButton');
    const profileForm = $('#admin-profile-form'); // Use the form ID


    let isEditing = false;
    const originalValues = {};

     // Function to display an error message below an input field for admin profile
    function displayAdminProfileError(inputElement, message) {
        // Find the specific error placeholder div using its class
        // Adjusted selector to find the correct error div based on the input's group
        var errorDiv = inputElement.closest('.form-group').find('.text-danger.small');
        errorDiv.html(message); // Use .html() to render <br> if needed
        inputElement.addClass('is-invalid'); // Add Bootstrap's invalid class
    }

    // Function to clear error messages for an input field for admin profile
    function clearAdminProfileError(inputElement) {
         // Adjusted selector to find the correct error div based on the input's group
         var errorDiv = inputElement.closest('.form-group').find('.text-danger.small');
        errorDiv.empty();
        inputElement.removeClass('is-invalid'); // Remove Bootstrap's invalid class
        inputElement.removeClass('is-valid'); // Also remove valid class on input
    }

    // --- Client-side Validation Functions for Admin Profile ---

    function validateAdminUsername() {
        clearAdminProfileError(nameInput); // Clear previous errors
        var username = nameInput.val().trim();
        if (username === '') {
            displayAdminProfileError(nameInput, 'Username is required.');
            return false;
        }
        if (username.length < 5 || username.length > 25) {
             displayAdminProfileError(nameInput, 'Username must be between 5 and 25 characters.');
             return false;
        }
        // Optionally add more checks (e.g., allowed characters)
        nameInput.addClass('is-valid');
        return true;
    }

    function validateAdminEmail() {
        clearAdminProfileError(emailInput);
        var email = emailInput.val().trim();
        if (email === '') {
            displayAdminProfileError(emailInput, 'Email is required.');
            return false;
        }
        var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(email)) {
            displayAdminProfileError(emailInput, 'Invalid email format.');
            return false;
        }
        if (email.length > 30) {
             displayAdminProfileError(emailInput, 'Email cannot exceed 30 characters.');
             return false;
        }
        emailInput.addClass('is-valid');
        return true;
    }

     function validateAdminAddress() {
        clearAdminProfileError(addressInput);
        var address = addressInput.val().trim();
        if (address === '') {
            displayAdminProfileError(addressInput, 'Address is required.');
            return false;
        }
         if (address.length > 50) {
             displayAdminProfileError(addressInput, 'Address cannot exceed 50 characters.');
             return false;
         }
        addressInput.addClass('is-valid');
        return true;
    }


    function enableEditMode() {
        isEditing = true;
        nameInput.prop('readonly', false);
        emailInput.prop('readonly', false);
        addressInput.prop('readonly', false);
        editButton.addClass('d-none');
        cancelButton.removeClass('d-none');
        saveButton.removeClass('d-none');

        // Store original values for potential cancellation
        originalValues.name = nameInput.val();
        originalValues.email = emailInput.val();
        originalValues.address = addressInput.val();

         // Clear any existing validation messages when entering edit mode
        $('.text-danger.small').empty();
        $('.form-control').removeClass('is-invalid is-valid');

        // Attach input/blur listeners when in edit mode
        nameInput.on('input blur', validateAdminUsername);
        emailInput.on('input blur', validateAdminEmail);
        addressInput.on('input blur', validateAdminAddress);
    }

    function disableEditMode() {
        isEditing = false;
        nameInput.prop('readonly', true);
        emailInput.prop('readonly', true);
        addressInput.prop('readonly', true);
        editButton.removeClass('d-none');
        cancelButton.addClass('d-none');
        saveButton.addClass('d-none');

        // Detach input/blur listeners when exiting edit mode
         nameInput.off('input blur');
         emailInput.off('input blur');
         addressInput.off('input blur');

         // Clear any client-side validation messages
         $('.text-danger.small').empty();
         $('.form-control').removeClass('is-invalid is-valid');
    }

    // Edit button functionality
    editButton.click(function () {
        enableEditMode();
    });

    // Cancel button functionality
    cancelButton.click(function () {
        disableEditMode();
        // Restore original values
        nameInput.val(originalValues.name);
        emailInput.val(originalValues.email);
        addressInput.val(originalValues.address);

        // Clear any server-side errors that might be displayed after a failed save
        // Consider a more specific selector if you have other alert-danger elements
        $('.alert.alert-danger').remove();
         $('.form-control').removeClass('is-invalid is-valid'); // Also clear input validation states

    });

    // Handle Save button functionality (client-side validation before submission)
    // Button type is 'submit' in HTML, so the form.submit() will be triggered by default.
    // We add a submit listener to the form itself for validation.
    profileForm.submit(function(e) {
        // Perform client-side validation only if in edit mode
        if (isEditing) {
            var isUsernameValid = validateAdminUsername();
            var isEmailValid = validateAdminEmail();
            var isAddressValid = validateAdminAddress();

            // If any validation fails, prevent form submission
            if (!isUsernameValid || !isEmailValid || !isAdminAddressValid) { // Corrected function call here
                e.preventDefault(); // Stop the form from submitting

                 // Optional: Scroll to the first invalid field
                 var firstInvalidField = $('.form-control.is-invalid').first();
                 if (firstInvalidField.length > 0) {
                      $('html, body').animate({
                         scrollTop: firstInvalidField.offset().top - 50 // Adjust offset
                     }, 500);
                 }
            }
            // If client-side validation passes, the form will submit normally.
        } else {
             // If not in edit mode but somehow the form is submitted
             e.preventDefault();
        }
    });

    // Handle Logout functionality
    // ... (your existing adminLogoutScript.js logic)

     // --- Initial Page Load Setup ---

     // Check for and display server-side flash attributes on page load
     // The HTML already handles displaying these alerts.

     // Re-apply server-side validation styles and display messages if present after redirect
     // This happens if Bean Validation failed on the server.
     // Thymeleaf typically adds the 'is-invalid' class if there are field errors
     $('.form-control.is-invalid').each(function() {
         var inputElement = $(this);
         var errorDiv = inputElement.closest('.form-group').find('.text-danger.small');
         if (errorDiv.length === 0) {
              // Ensure client-side error div is present
              $('<div class="text-danger small mt-1"></div>').insertAfter(inputElement);
         }
     });

      // If there are server-side field errors displayed by Thymeleaf,
      // ensure the form is in edit mode.
      if ($('.form-control.is-invalid').length > 0 || $('.alert.alert-danger').length > 0) { // Also check for general error alerts
           enableEditMode(); // Automatically switch to edit mode
           // Optional: Scroll to the top alert or first invalid field
            var firstErrorAlert = $(".alert.alert-danger").first();
            if (firstErrorAlert.length > 0) {
                $('html, body').animate({
                    scrollTop: firstErrorAlert.offset().top
                }, 500);
            } else {
                var firstInvalidField = $('.form-control.is-invalid').first();
                 if (firstInvalidField.length > 0) {
                      $('html, body').animate({
                         scrollTop: firstInvalidField.offset().top - 50
                     }, 500);
                 }
            }
      }


});