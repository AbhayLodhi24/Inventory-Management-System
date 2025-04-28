$(document).ready(function() {
    const supplierModal = new bootstrap.Modal($('#SupplierModal'));
    const addSupplierBtn = $('#addSupplierBtn');
    const supplierForm = $('#supplierForm'); // Select the form inside the modal
    const editSupplierModal = new bootstrap.Modal($('#editSupplierModal'));
    const editSupplierForm = $('#editSupplierModal form');

    // Open the add supplier modal
    addSupplierBtn.on('click', function() {
        $('#supplierModalLabel').text('Add Supplier');
        $('#modalSubmitBtn').text('Add Supplier');
        supplierForm[0].reset(); // Clear the form fields
        $('#SupplierModal .text-danger').empty(); // Clear any previous error messages
        supplierModal.show();
    });

    // Handle the add supplier form submission
    supplierForm.on('submit', function(event) {
        event.preventDefault(); // Prevent the default form submission

        const form = $(this);
        $.ajax({
            type: form.attr('method'),
            url: form.attr('action'),
            data: form.serialize(),
            success: function(response) {
                if ($(response).find('.text-danger').length > 0 || $(response).find('#emailError').length > 0 || $(response).find('#generalError').length > 0) {
                    // If there are validation errors in the response, update the modal content
                    $('#SupplierModal .modal-content').html(response);
                    supplierModal.show(); // Ensure the modal is shown
                } else {
                    // If no errors, show success message and reload the supplier table
                    alert('Supplier added successfully');
                    supplierModal.hide();
                    resetSupplierForm();
                    // Reload the supplier table fragment
                    $.get('/admin/suppliers .content > div', function(newContent) {
                        $('.content > div').html(newContent);
                    });
                }
            },
            error: function(xhr, status, error) {
                alert('Error adding supplier: ' + error);
            }
        });
    });

    // Open the edit supplier modal and populate data
    $(document).on('click', '#editSupplierBtn', function() {
        $('#editSupplierModalLabel').text("Edit Supplier");
        editSupplierModal.show();
        const supplierId = $(this).data('id');
        const name = $(this).data('name');
        const email = $(this).data('email');
        const phone = $(this).data('phone');
        const address = $(this).data('address');

        $('#editSupplierModal #editSupplierId').val(supplierId);
        $('#editSupplierModal #editSupplierName').val(name);
        $('#editSupplierModal #editSupplierEmail').val(email);
        $('#editSupplierModal #editSupplierPhno').val(phone);
        $('#editSupplierModal #editSupplierAddr').val(address);
        $('#editSupplierModal .text-danger').empty(); // Clear any previous error messages
    });

    // Handle the edit supplier form submission
    editSupplierForm.on('submit', function(event) {
        event.preventDefault(); // Prevent the default form submission

        const form = $(this);
        $.ajax({
            type: form.attr('method'),
            url: form.attr('action').replace('{id}', $('#editSupplierId').val()), // Include the ID in the URL
            data: form.serialize(),
            success: function(response) {
                if ($(response).find('.text-danger').length > 0 || $(response).find('#editEmailError').length > 0 || $(response).find('#editGeneralError').length > 0) {
                    // If there are validation errors in the response, update the modal content
                    $('#editSupplierModal .modal-content').html(response);
                    editSupplierModal.show(); // Ensure the modal is shown
                } else {
                    // If no errors, show success message and reload the supplier table
                    alert('Supplier updated successfully');
                    editSupplierModal.hide();
                    resetEditSupplierForm();
                    // Reload the supplier table fragment
                    $.get('/admin/suppliers .content > div', function(newContent) {
                        $('.content > div').html(newContent);
                    });
                }
            },
            error: function(xhr, status, error) {
                alert('Error updating supplier: ' + error);
            }
        });
    });
});

function resetSupplierForm() {
    $('#supplierModal form')[0].reset();
    $('#SupplierModal .text-danger').empty(); // Clear any error messages on reset
}

function resetEditSupplierForm() {
    $('#editSupplierModal form')[0].reset();
    $('#editSupplierModal .text-danger').empty(); // Clear any error messages on reset
}