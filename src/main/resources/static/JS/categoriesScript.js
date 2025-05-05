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
                handleServerError(xhr);
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
                handleServerError(xhr);
            }
        });
    });
 
    function handleServerError(xhr) {
        if (xhr.status === 400) {
            const tempDiv = $('<div>').html(xhr.responseText);
            $('#categoryName').val(tempDiv.find('#categoryName').val());
            $('#description').val(tempDiv.find('#description').val());
            $('#categoryNameError').html(tempDiv.find('#categoryNameError').html());
            $('#descriptionError').html(tempDiv.find('#descriptionError').html());
 
            // For the edit modal
            $('#editCategoryName').val(tempDiv.find('#editCategoryName').val());
            $('#editDescription').val(tempDiv.find('#editDescription').val());
            $('#editCategoryId').val(tempDiv.find('#editCategoryId').val()); // If you are re-populating the ID
            $('#editCategoryNameError').html(tempDiv.find('#editCategoryNameError').html());
            $('#editDescriptionError').html(tempDiv.find('#editDescriptionError').html());
 
            const generalError = tempDiv.find('.alert.alert-danger');
            if (generalError.length) {
                alert(generalError.text());
            }
            // Keep the modal open by NOT calling modal('hide');
            // You'll need to identify which modal is open and avoid hiding it.
            if ($('#CategoryModal').is(':visible')) {
                categoryModal.show();
            } else if ($('#editCategoryModal').is(':visible')) {
                editCategoryModal.show();
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