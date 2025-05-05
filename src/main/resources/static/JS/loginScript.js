$(document).ready(function() {
    var loginContainer = $('#loginContainer');
    var registerContainer = $('#registerContainer');
    var registerButton = $('#registerBtn');
    var loginLink = $('#loginLink');

    // Get the registration form and its fields
    var registerForm = $('#register-form');
    var regUsernameInput = $('#register-username');
    var regEmailInput = $('#register-email');
    var regPasswordInput = $('#register-password');
    var regAddressInput = $('#register-address');

function displayError(inputElement, message) {
    var errorId = inputElement.attr('id').replace('register-', '') + '-error';
    $('#' + errorId).html('<div class="text-danger small mt-1">' + message + '</div>');
    inputElement.addClass('is-invalid');
}
function clearError(inputElement) {
    var errorId = inputElement.attr('id').replace('register-', '') + '-error';
    $('#' + errorId).html('');
    inputElement.removeClass('is-invalid').removeClass('is-valid');
}

    // --- Client-side Validation Functions ---

    function validateUsername() {
        clearError(regUsernameInput);
        var username = regUsernameInput.val().trim();
        if (username === '') {
            displayError(regUsernameInput, 'Username is required.');
            return false;
        }
        if (username.length < 5 || username.length > 25) {
             displayError(regUsernameInput, 'Username must be between 5 and 25 characters.');
             return false;
        }
        // Optionally add more checks (e.g., allowed characters)
        regUsernameInput.addClass('is-valid'); // Add Bootstrap's valid class if passes
        return true;
    }

    function validateEmail() {
        clearError(regEmailInput);
        var email = regEmailInput.val().trim();
        if (email === '') {
            displayError(regEmailInput, 'Email is required.');
            return false;
        }
        // Simple email regex - for full validation rely on server-side @Email
        var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(email)) {
            displayError(regEmailInput, 'Invalid email format.');
            return false;
        }
        if (email.length < 5 || email.length > 30) {
             displayError(regEmailInput, 'Email must be between 5 and 30 characters.');
             return false;
        }
         regEmailInput.addClass('is-valid');
        return true;
    }

function validatePassword() {
        clearError(regPasswordInput);
        var password = regPasswordInput.val(); // Use .val() without trim() for password
        var isValid = true; // Flag to track overall validity
        var errorMessages = []; // Array to store multiple error messages

        if (password === '') {
            displayError(regPasswordInput, 'Password is required.');
            return false; // Stop further checks if password is empty
        }

        // 1. Minimum Length Check
        if (password.length < 6) {
            errorMessages.push('Must be at least 6 characters.');
            isValid = false;
        }
     if (password.length > 30) {
                 errorMessages.push('Password cannot exceed 30 characters.');
                 isValid = false;
             }

        // 2. Uppercase Letter Check
        if (!/[A-Z]/.test(password)) {
            errorMessages.push('Must contain at least one uppercase letter.');
            isValid = false;
        }

        // 3. Lowercase Letter Check
        if (!/[a-z]/.test(password)) {
            errorMessages.push('Must contain at least one lowercase letter.');
            isValid = false;
        }

        // 4. Number Check
        if (!/[0-9]/.test(password)) {
            errorMessages.push('Must contain at least one number.');
            isValid = false;
        }

        // 5. Special Character Check (Example set of special characters)
        // You can adjust the characters inside the square brackets [] based on your policy
        if (!/[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?~]/.test(password)) {
             errorMessages.push('Must contain at least one special character (e.g., !@#$%^&*).');
            isValid = false;
        }

        // If not valid, display all accumulated error messages
        if (!isValid) {
            // Join all error messages into a single string with line breaks
            displayError(regPasswordInput, errorMessages.join('<br>'));
            return false;
        }

        // If all checks pass
        regPasswordInput.addClass('is-valid');
        return true;
    }

     function validateAddress() {
        clearError(regAddressInput);
        var address = regAddressInput.val().trim();
        if (address === '') {
            displayError(regAddressInput, 'Address is required.');
            return false;
        }
         if (address.length < 2 || address.length > 50) {
             displayError(regAddressInput, 'Address must be between 2 and 50 characters.');
             return false;
         }
        regAddressInput.addClass('is-valid');
        return true;
    }


    // --- Event Listeners ---

    // Initial check and display logic (keep this from previous step)
    var registrationErrorPresent = $('.alert.alert-danger span:contains("Username")').length > 0; // Check for duplicate username error
     var validationErrorsPresent = $('.alert.alert-danger:has(span)').length > 0; // Check for general validation error alerts

    if (registrationErrorPresent || validationErrorsPresent || window.location.hash === '#registerContainer') {
        loginContainer.hide();
        registerContainer.show();
        $("#form-title").text("Register");
         // Scroll to the first error message or the registration form
         var firstErrorAlert = $(".alert.alert-danger").first();
         if (firstErrorAlert.length > 0) {
             $('html, body').animate({
                 scrollTop: firstErrorAlert.offset().top
             }, 500);
         } else if (window.location.hash === '#registerContainer') {
              $('html, body').animate({
                 scrollTop: registerContainer.offset().top
             }, 500);
         }


    } else {
        loginContainer.show();
        registerContainer.hide();
        $("#form-title").text("Login");
    }

    // Handle click to switch from login to register
    registerButton.click(function (e) {
        e.preventDefault();
        // Clear any previous validation messages when switching forms
        $('.text-danger').remove();
        $('.form-control').removeClass('is-invalid is-valid');
        loginContainer.hide();
        registerContainer.show();
        $("#form-title").text("Register");
        history.pushState(null, null, '#registerContainer');
    });

     // Handle click to switch from register to login
    loginLink.click(function (e) {
        e.preventDefault();
         // Clear any previous validation messages when switching forms
        $('.text-danger').remove();
        $('.form-control').removeClass('is-invalid is-valid');
        registerContainer.hide();
        loginContainer.show();
        $("#form-title").text("Login");
        history.pushState(null, null, '#loginContainer');
    });


    // Handle registration form submission
    registerForm.submit(function(e) {
        // Perform validation for all fields
        var isUsernameValid = validateUsername();
        var isEmailValid = validateEmail();
        var isPasswordValid = validatePassword();
        var isAddressValid = validateAddress();

        // If any validation fails, prevent form submission
        if (!isUsernameValid || !isEmailValid || !isPasswordValid || !isAddressValid) {
            e.preventDefault(); // Stop the form from submitting
             // Optional: Scroll to the first invalid field
             var firstInvalidField = $('.form-control.is-invalid').first();
             if (firstInvalidField.length > 0) {
                  $('html, body').animate({
                     scrollTop: firstInvalidField.offset().top - 50 // Adjust offset as needed
                 }, 500);
             }
        }
        // If all valid, the form will submit normally
    });

    // Clear validation errors as the user types
    regUsernameInput.on('input', validateUsername);
    regEmailInput.on('input', validateEmail);
    regPasswordInput.on('input', validatePassword);
    regAddressInput.on('input', validateAddress);

    // Re-validate when field loses focus (blur)
    regUsernameInput.on('blur', validateUsername);
    regEmailInput.on('blur', validateEmail);
    regPasswordInput.on('blur', validatePassword);
    regAddressInput.on('blur', validateAddress);


});
