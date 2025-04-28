$(document).ready(function() {
    const addSupplierModal = $('#addSupplierModal');
    const addSupplierForm = $('#addSupplierForm');
    const addSupplierModalLabel = $('#addSupplierModalLabel');
    const editSupplierModalLabel = $('#editSupplierModalLabel');
    const supplierIdInput = $('#supplierId');

    function updateSupplierErrorDisplay(fieldId, errorMessage) {
        const inputElement = $('#' + fieldId);
        if (errorMessage) {
            inputElement.addClass('is-invalid');
            alert(errorMessage);
        } else {
            inputElement.removeClass('is-invalid');
        }
    }

    addSupplierForm.submit(function(event) {
        event.preventDefault(); // Prevent default form submission

        let shouldSubmit = true; // Flag to control submission

        // Client-side validation based on SupplierDto constraints
        if ($('#supplierName').val().trim() === "") {
            updateSupplierErrorDisplay('supplierName', 'Supplier name is required.');
            shouldSubmit = false;
        } else if ($('#supplierName').val().trim().length > 50) {
            updateSupplierErrorDisplay('supplierName', 'Supplier name cannot exceed 50 characters.');
            shouldSubmit = false;
        } else if ($('#supplierName').val().trim().length < 3) {
            updateSupplierErrorDisplay('supplierName', 'Supplier name must be at least 3 characters.');
            shouldSubmit = false;
        } else {
            updateSupplierErrorDisplay('supplierName', '');
        }

        const emailValue = $('#supplierEmail').val().trim();
        if (emailValue === "") {
            updateSupplierErrorDisplay('supplierEmail', 'Supplier email is required.');
            shouldSubmit = false;
        } else if (!isValidEmail(emailValue)) {
            updateSupplierErrorDisplay('supplierEmail', 'Invalid email format.');
            shouldSubmit = false;
        } else {
            updateSupplierErrorDisplay('supplierEmail', '');
        }

        const phoneValue = $('#supplierPhno').val().trim();
        if (phoneValue === "") {
            updateSupplierErrorDisplay('supplierPhno', 'Phone number is required.');
            shouldSubmit = false;
        } else if (!/^[0-9]{10}$/.test(phoneValue)) {
            updateSupplierErrorDisplay('supplierPhno', 'Phone number must be exactly 10 digits.');
            shouldSubmit = false;
        } else {
            updateSupplierErrorDisplay('supplierPhno', '');
        }

        if ($('#supplierAddr').val().trim() === "") {
            updateSupplierErrorDisplay('supplierAddr', 'Address is required.');
            shouldSubmit = false;
        } else if ($('#supplierAddr').val().trim().length < 5) {
            updateSupplierErrorDisplay('supplierAddr', 'Address must be at least 5 characters.');
            shouldSubmit = false;
        } else if ($('#supplierAddr').val().trim().length > 100) {
            updateSupplierErrorDisplay('supplierAddr', 'Address cannot exceed 100 characters.');
            shouldSubmit = false;
        } else {
            updateSupplierErrorDisplay('supplierAddr', '');
        }

        if (shouldSubmit) {
            const formData = $(this).serialize();
            const supplierId = supplierIdInput.val();
            const supplierName = $('#supplierName').val().trim();
            const supplierEmail = $('#supplierEmail').val().trim();
            const supplierPhno = $('#supplierPhno').val().trim();
            const supplierAddr = $('#supplierAddr').val().trim();

            let isDuplicateSupplier = false;
            if (typeof existingSupplierDetails !== 'undefined') {
                for (const supplier of existingSupplierDetails) {
                    if (supplier.supplierName === supplierName &&
                        supplier.supplierEmail === supplierEmail &&
                        supplier.supplierPhno === supplierPhno &&
                        supplier.supplierAddr === supplierAddr &&
                        parseInt(supplier.supplierId) !== parseInt(supplierIdInput.val())) { // Exclude the current supplier being edited
                        isDuplicateSupplier = true;
                        break;
                    }
                }
            }

            if (isDuplicateSupplier) {
                alert("A supplier with the same details (name, email, phone, address) already exists. You can't save this supplier.");
            } else {
                const actionUrl = supplierId === '0' ? '/admin/suppliers/addSupplier' : '/admin/suppliers/editSupplier';
                addSupplierForm.attr('action', actionUrl);
                submitSupplierFormData(formData, actionUrl);
            }
        }
    });

    function isValidEmail(email) {
        // Basic email validation regex
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return emailRegex.test(email);
    }

    function submitSupplierFormData(formData, url) {
        $.ajax({
            url: url,
            type: 'POST',
            data: formData,
            success: function(response) {
                addSupplierModal.modal('hide');
                window.location.reload();
            },
            error: function(xhr, status, error) {
                handleSupplierServerError(xhr);
            }
        });
    }

    let existingSupplierDetails = []; // Store fetched existing supplier details

    function fetchExistingSupplierDetails() {
        return $.ajax({
            url: '/admin/suppliers/existingDetails', // Endpoint to get existing supplier details
            type: 'GET'
        });
    }

    addSupplierModal.on('show.bs.modal', function() {
        fetchExistingSupplierDetails().done(function(data) {
            existingSupplierDetails = data;
        }).fail(function(error) {
            console.error("Error fetching existing supplier details:", error);
        });

        addSupplierForm.find('.is-invalid').removeClass('is-invalid');
    });

    $('.edit-supplier-btn').on('click', function() {
        const supplierId = $(this).data('supplier-id');
        const supplierName = $(this).data('supplier-name');
        const supplierEmail = $(this).data('supplier-email');
        const supplierPhno = $(this).data('supplier-phno');
        const supplierAddr = $(this).data('supplier-addr');

        addSupplierModalLabel.addClass('d-none'); // Hide "Add New Supplier" title
        editSupplierModalLabel.removeClass('d-none'); // Show "Edit Supplier" title

        supplierIdInput.val(supplierId);
        $('#supplierName').val(supplierName);
        $('#supplierEmail').val(supplierEmail);
        $('#supplierPhno').val(supplierPhno);
        $('#supplierAddr').val(supplierAddr);
    });

    function handleSupplierServerError(xhr) {
        if (xhr.status === 400) {
            const tempDiv = $('<div>').html(xhr.responseText);
            $('#supplierName').val(tempDiv.find('#supplierName').val());
            $('#supplierEmail').val(tempDiv.find('#supplierEmail').val());
            $('#supplierPhno').val(tempDiv.find('#supplierPhno').val());
            $('#supplierAddr').val(tempDiv.find('#supplierAddr').val());
            const generalError = tempDiv.find('.alert.alert-danger');
            if (generalError.length) {
                alert(generalError.text());
            }
            // Keep the modal open
        } else {
            alert("An error occurred while adding/editing the supplier.");
            console.error("Error adding/editing supplier:", error);
        }
    }

    addSupplierModal.on('hidden.bs.modal', function () {
        addSupplierModalLabel.removeClass('d-none');
        editSupplierModalLabel.addClass('d-none');
        addSupplierForm[0].reset();
        addSupplierForm.find('.is-invalid').removeClass('is-invalid');
        supplierIdInput.val(0);
        addSupplierForm.attr('action', '/admin/suppliers/addSupplier');
    });
});