$(document).ready(function () {
    const categoryModal = new bootstrap.Modal($('#CategoryModal'));
    const editCategoryModal = new bootstrap.Modal($('#editCategoryModal'));
 
    // Function to display error messages
    function displayError(elementId, message) {
        $(`#${elementId}`).text(message);
    }
 
    // Function to clear error messages
    function clearError(elementId) {
        $(`#${elementId}`).text('');
    }
 
    // Client-side validation for add category form
    function validateAddCategoryForm() {
        let isValid = true;
        const name = $('#categoryName').val().trim();
        const description = $('#description').val().trim();
 
        if (name.length < 3 || name.length > 25) {
            displayError('categoryNameError', 'Name must be between 3 and 25 characters.');
            isValid = false;
        } else {
            clearError('categoryNameError');
        }
 
        if (description.length < 5 || description.length > 250) {
            displayError('descriptionError', 'Description must be between 5 and 250 characters.');
            isValid = false;
        } else {
            clearError('descriptionError');
        }
 
        return isValid;
    }
 
    // Client-side validation for edit category form
    function validateEditCategoryForm() {
        let isValid = true;
        const name = $('#editCategoryName').val().trim();
        const description = $('#editDescription').val().trim();
 
        if (name.length < 3 || name.length > 25) {
            displayError('editCategoryNameError', 'Name must be between 3 and 25 characters.');
            isValid = false;
        } else {
            clearError('editCategoryNameError');
        }
 
        if (description.length < 5 || description.length > 250) {
            displayError('editDescriptionError', 'Description must be between 5 and 250 characters.');
            isValid = false;
        } else {
            clearError('editDescriptionError');
        }
 
        return isValid;
    }
 
    // Show the "Add Category" modal when the button is clicked
    $("#addCategoryBtn").on("click", function () {
        $("#CategoryModal").modal("show");
        $("#categoryForm")[0].reset(); // Clear form on open
        $("#categoryNameError").text("");
        $("#descriptionError").text("");
        $('#CategoryModal .text-danger.server-error').empty(); // Clear any previous server error messages
        $('#CategoryModal .text-danger.client-error').empty(); // Clear any previous client error messages
    });
 
    // Handle the add category form submission
    $("#categoryForm").submit(function (event) {
        event.preventDefault(); // Prevent the default form submission
        if (!validateAddCategoryForm()) {
            return; // Stop submission if client-side validation fails
        }
        const formData = $(this).serialize();
        $.ajax({
            url: '/admin/categories/addCategory',
            type: 'POST',
            data: formData,
            success: function (response) {
                $("#CategoryModal").modal("hide");
                window.location.reload();
            },
            error: function (xhr, status, error) {
                handleServerError(xhr, 'CategoryModal'); // Pass the modal ID
            }
        });
    });
 
    $("#editCategoryForm").submit(function (event) {
        event.preventDefault(); // Prevent the default form submission
        if (!validateEditCategoryForm()) {
            return; // Stop submission if client-side validation fails
        }
        const formData = $(this).serialize();
        $.ajax({
            url: '/admin/categories/editCategory',
            type: 'POST',
            data: formData,
            success: function (response) {
                $("#editCategoryModal").modal("hide");
                window.location.reload();
            },
            error: function (xhr, status, error) {
                handleServerError(xhr, 'editCategoryModal'); // Pass the modal ID
            }
        });
    });
 
    function handleServerError(xhr, modalId) {
        if (xhr.status === 400) {
            // The server returned the modal fragment with validation errors
            const tempDiv = $('<div>').html(xhr.responseText);
            const newBody = tempDiv.find('.modal-body').html();
            const newFooter = tempDiv.find('.modal-footer').html();
            const modalElement = $('#' + modalId);
            const modalInstance = bootstrap.Modal.getInstance(modalElement); // Get the existing instance
 
            if (modalInstance) {
                // Update the modal content
                modalElement.find('.modal-body').html(newBody);
                modalElement.find('.modal-footer').html(newFooter);
 
                // Keep the modal open by showing it again
                modalInstance.show();
            } else {
                // This should ideally not happen if the modal was shown before
                console.error('Modal instance not found for:', modalId);
            }
        } else {
            alert("An error occurred while adding/editing the category.");
            console.error("Error adding/editing category:", xhr);
        }
    }
 
    // Show the "Edit Category" modal and populate data
    $(document).on("click", ".edit-btn", function () {
        const categoryId = $(this).data("id");
        const categoryName = $(this).data("name");
        const categoryDescription = $(this).data("description");
 
        $("#editCategoryId").val(categoryId);
        $("#editCategoryName").val(categoryName);
        $("#editDescription").val(categoryDescription);
        $("#editCategoryModal").modal("show");
        $("#editCategoryNameError").text(""); // Clear errors on open
        $("#editDescriptionError").text("");
        $('#editCategoryModal .text-danger.server-error').empty(); // Clear any previous server error messages
        $('#editCategoryModal .text-danger.client-error').empty(); // Clear any previous client error messages
    });
 
    // Handle Logout functionality (no changes needed here)
    $('#logoutButton').click(function (e) {
        e.preventDefault();
        const loginName = "admin";
        const loginEmail = "admin@gmail.com";
        $('#modalUserName').text(loginName);
        $('#modalUserEmail').text(loginEmail);
        $('#logoutModal').modal('show');
    });
 
    $('#cancelLogout').click(function () {
        $('#logoutModal').modal('hide');
    });
 
    $('#confirmLogout').click(function () {
        $('#logoutModal').modal('hide');
        window.location.href = '../Login/login.html';
    });
 
    $('#CategoryModal, #editCategoryModal').on('hidden.bs.modal', function (event) {
        $(this).find('.text-danger.server-error').empty(); // Clear server errors on close
        $(this).find('.text-danger.client-error').empty(); // Clear client errors on close
 
        // Try to focus back on the add button if it exists and triggered the modal
        if (event.relatedTarget === $("#addCategoryBtn")[0]) {
            $("#addCategoryBtn").focus();
        } else if (event.relatedTarget && $(event.relatedTarget).is('.edit-btn')) {
            // If an edit button triggered the edit modal, focus back on it
            $(event.relatedTarget).focus();
        } else {
            // Optionally focus on another relevant element if the trigger is not found
            // For example, you might want to focus on the main table or a specific section.
            // $('#yourCategoryTable').focus();
        }
    });
});
 
function resetCategoryForm() {
    $("#categoryForm")[0].reset();
    $("#categoryNameError").text("");
    $("#descriptionError").text("");
}
 
function resetEditCategoryForm() {
    $("#editCategoryForm")[0].reset();
    $("#editCategoryNameError").text("");
    $("#editDescriptionError").text("");
}