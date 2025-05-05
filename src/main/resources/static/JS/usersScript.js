$(document).ready(function() {
    const addUserModal = $('#addUserModal');
    const addUserForm = $('#addUserForm');
    const userIdInput = $('#userId');
    let isErrorState = false;

    function updateErrorDisplay(fieldId, errorMessage) {
        const inputElement = $('#' + fieldId);
        const errorElement = inputElement.next('.text-danger');
        if (errorMessage) {
            if (errorElement.length === 0) {
                inputElement.after(`<div class="text-danger small">${errorMessage}</div>`);
            } else {
                errorElement.text(errorMessage);
            }
            inputElement.addClass('is-invalid');
            console.log(`Error for ${fieldId}: ${errorMessage}`);
        } else {
            inputElement.removeClass('is-invalid');
            if (errorElement.length > 0) {
                errorElement.remove();
            }
            console.log(`Cleared error for ${fieldId}`);
        }
    }

    function validateField(fieldId, validationFn) {
        const inputElement = $('#' + fieldId);
        inputElement.on('input', function() {
            const errorMessage = validationFn(inputElement.val());
            updateErrorDisplay(fieldId, errorMessage);
        });
    }

    validateField('username', function(value) {
        if (value.trim() === "") {
            return 'Username is required.';
        } else if (value.trim().length < 3 || value.trim().length > 50) {
            return 'Username must be between 3 and 50 characters.';
        }
        return '';
    });

    validateField('password', function(value) {
        let errorMessage = '';
        if (value === "") {
            errorMessage += 'Password is required. ';
        } else {
            if (value.length < 8) {
                errorMessage += 'Password must be at least 8 characters. ';
            }
            if (!/[A-Z]/.test(value)) {
                errorMessage += 'Password must contain at least one uppercase letter. ';
            }
            if (!/[0-9]/.test(value)) {
                errorMessage += 'Password must contain at least one number. ';
            }
            if (!/[!@#$%^&*(),.?":{}|<>]/.test(value)) {
                errorMessage += 'Password must contain at least one special character. ';
            }
        }
        return errorMessage.trim();
    });

    validateField('email', function(value) {
        if (value.trim() === "") {
            return 'Email is required.';
        } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(value)) {
            return 'Invalid email format.';
        } else if (value.trim().length > 100) {
            return 'Email cannot exceed 100 characters.';
        }
        return '';
    });

    validateField('address', function(value) {
        if (value.trim() === "") {
            return 'Address is required.';
        } else if (value.trim().length < 2 || value.trim().length > 255) {
            return 'Address must be between 2 and 255 characters.';
        }
        return '';
    });

    addUserForm.on('submit', function(event) {
        event.preventDefault();
        isErrorState = false;

        let hasClientSideErrors = false;
        const clientSideErrorMessages = [];

        $('#username, #password, #email, #address').each(function() {
            const fieldId = $(this).attr('id');
            const errorMessage = $(this).next('.text-danger').text();
            if (errorMessage) {
                clientSideErrorMessages.push(errorMessage);
                hasClientSideErrors = true;
            }
        });

        if (hasClientSideErrors) {
            alert(clientSideErrorMessages.join('\n'));
            addUserModal.modal('show');
            isErrorState = true;
        } else {
            const formData = $(this).serialize();
            $.ajax({
                url: '/admin/users/addUser',
                type: 'POST',
                data: formData,
                success: function(response) {
                    const tempDiv = $('<div>').html(response);
                    const serverErrorAlert = tempDiv.find('.alert.alert-danger');
                    const invalidFields = tempDiv.find('.is-invalid');
                    const serverErrorMessages = [];

                    if (serverErrorAlert.length > 0) {
                        serverErrorMessages.push(serverErrorAlert.text());
                    }
                    tempDiv.find('.is-invalid').each(function() {
                        const label = $(this).siblings('label').text();
                        const errorMessage = $(this).next('.invalid-feedback').text();
                        if (label && errorMessage) {
                            serverErrorMessages.push(`${label}: ${errorMessage}`);
                        } else if (label) {
                            serverErrorMessages.push(`Please correct the ${label.toLowerCase()}.`);
                        } else {
                            serverErrorMessages.push("Please correct the highlighted fields.");
                        }
                    });

                    if (serverErrorMessages.length > 0) {
                        alert(serverErrorMessages.join('\n'));
                        addUserModal.modal('show');
                        isErrorState = true;
                    } else {
                        addUserModal.modal('hide');
                        window.location.href = '/admin/users'; // Redirect on successful add
                    }
                },
                error: function(xhr, status, error) {
                    let errorMessage = "An error occurred while adding the user.";
                    if (xhr.responseText) {
                        const tempDiv = $('<div>').html(xhr.responseText);
                        const serverErrorMessageElement = tempDiv.find('.alert.alert-danger');
                        if (serverErrorMessageElement.length) {
                            errorMessage = serverErrorMessageElement.text();
                        }
                        addUserModal.find('.modal-content').html(tempDiv.find('.modal-content').html());
                    }
                    alert(errorMessage);
                    addUserModal.modal('show');
                    isErrorState = true;
                }
            });
        }
    });

    addUserModal.on('hidden.bs.modal', function (e) {
        if (!isErrorState) {
            // Only redirect if the modal was closed without an error
            window.location.href = '/admin/users';
        } else {
            // Reset the error state so that the next time the modal is closed
            // without an error, the redirection happens.
            isErrorState = false;
        }
        addUserForm[0].reset();
        addUserForm.find('.is-invalid').removeClass('is-invalid');
        userIdInput.val(0);
        addUserForm.attr('action', '/admin/users/addUser');
    });

    $('#addUserModal .btn-secondary[data-bs-dismiss="modal"]').on('click', function() {
        addUserModal.modal('hide'); // Trigger the 'hidden.bs.modal' event
    });
});

 