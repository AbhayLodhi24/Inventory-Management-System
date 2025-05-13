$(document).ready(function() {
     const supplierModal = new bootstrap.Modal($('#SupplierModal'));
     const addSupplierBtn = $('#addSupplierBtn');
     const supplierForm = $('#supplierForm'); // Select the form inside the modal
     const editSupplierModal = new bootstrap.Modal($('#editSupplierModal'));
     const editSupplierForm = $('#editSupplierForm'); // Select the edit form
 
     // Function to display error messages
     function displayError(elementId, message) {
         $(`#${elementId}`).text(message);
     }
 
     // Function to clear error messages
     function clearError(elementId) {
         $(`#${elementId}`).text('');
     }
 
     // Client-side validation for add supplier form
     function validateAddSupplierForm() {
         let isValid = true;
 
         const name = $('#supplierName').val().trim();
         const address = $('#supplierAddr').val().trim();
         const phone = $('#supplierPhno').val().trim();
         const email = $('#supplierEmail').val().trim(); // Get the email value
 
         if (name.length < 3) {
             displayError('supplierNameError', 'Name must be at least 3 characters.');
             isValid = false;
         } else {
             clearError('supplierNameError');
         }
 
         if (address.length < 5 || address.length > 250) {
             displayError('supplierAddrError', 'Address must be between 5 and 250 characters.');
             isValid = false;
         } else {
             clearError('supplierAddrError');
         }
 
         if (!/^\d{10}$/.test(phone)) {
             displayError('supplierPhnoError', 'Phone number must be exactly 10 digits.');
             isValid = false;
         } else {
             clearError('supplierPhnoError');
         }
 
         // Basic email format validation (you might want a more robust one)
         if (email && !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
             displayError('supplierEmailError', 'Invalid email format.');
             isValid = false;
         } else {
             clearError('supplierEmailError');
         }
 
         return isValid;
     }
 
     // Client-side validation for edit supplier form
     function validateEditSupplierForm() {
         let isValid = true;
 
         const name = $('#editSupplierName').val().trim();
         const address = $('#editSupplierAddr').val().trim();
         const phone = $('#editSupplierPhno').val().trim();
         const email = $('#editSupplierEmail').val().trim(); // Get the email value
 
         if (name.length < 3) {
             displayError('editSupplierNameError', 'Name must be at least 3 characters.');
             isValid = false;
         } else {
             clearError('editSupplierNameError');
         }
 
         if (address.length < 5 || address.length > 250) {
             displayError('editSupplierAddrError', 'Address must be between 5 and 250 characters.');
             isValid = false;
         } else {
             clearError('editSupplierAddrError');
         }
 
         if (!/^\d{10}$/.test(phone)) {
             displayError('editSupplierPhnoError', 'Phone number must be exactly 10 digits.');
             isValid = false;
         } else {
             clearError('editSupplierPhnoError');
         }
 
         // Basic email format validation (you might want a more robust one)
         if (email && !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
             displayError('editSupplierEmailError', 'Invalid email format.');
             isValid = false;
         } else {
             clearError('editSupplierEmailError');
         }
 
         return isValid;
     }
 
     // Open the add supplier modal
     addSupplierBtn.on('click', function() {
         $('#supplierModalLabel').text('Add Supplier');
         $('#modalSubmitBtn').text('Add Supplier');
         supplierForm[0].reset(); // Clear the form fields
         $('#SupplierModal .text-danger.server-error').empty(); // Clear any previous server error messages
         $('#SupplierModal .text-danger.client-error').empty(); // Clear any previous client error messages
         supplierModal.show();
     });
 
     // Handle the add supplier form submission
     supplierForm.on('submit', function(event) {
         event.preventDefault(); // Prevent the default form submission
 
         if (!validateAddSupplierForm()) {
             return; // Stop submission if client-side validation fails
         }
 
         // Convert email to lowercase before serializing the form data
         $('#supplierEmail').val($('#supplierEmail').val().toLowerCase());
 
         const form = $(this);
         $.ajax({
             type: form.attr('method'),
             url: form.attr('action'),
             data: form.serialize(),
             success: function(response) {
                 $("#SupplierModal").modal("hide");
                 window.location.reload();
             },
             error: function(xhr, status, error) {
                 handleServerError(xhr, 'SupplierModal'); // Pass the modal ID
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
         $('#editSupplierModal .text-danger.server-error').empty(); // Clear any previous server error messages
         $('#editSupplierModal .text-danger.client-error').empty(); // Clear any previous client error messages
     });
 
     // Handle the edit supplier form submission
     editSupplierForm.on('submit', function(event) {
         event.preventDefault(); // Prevent the default form submission
 
         if (!validateEditSupplierForm()) {
             return; // Stop submission if client-side validation fails
         }
 
         // Convert email to lowercase before serializing the form data
         $('#editSupplierEmail').val($('#editSupplierEmail').val().toLowerCase());
 
         const formData = $(this).serialize();
         console.log("Form Data:", formData);
         $.ajax({
             url: '/admin/suppliers/editSupplier', // Updated URL to the single POST endpoint
             type: 'POST',
             data: formData,
             success: function(response) {
                 $("#editSupplierModal").modal("hide"); // Hide the edit supplier modal
                 window.location.reload();
             },
             error: function(xhr, status, error) {
                 handleServerError(xhr, 'editSupplierModal'); // Pass the modal ID
             }
         });
         // You can temporarily log the data instead of making the AJAX call
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
             alert("An error occurred while adding/editing the supplier.");
             console.error("Error adding/editing supplier:", xhr);
         }
     }
 
     $('#SupplierModal, #editSupplierModal').on('hidden.bs.modal', function (event) {
         $(this).find('.text-danger.server-error').empty(); // Clear server errors on close
         $(this).find('.text-danger.client-error').empty(); // Clear client errors on close
 
         // Try to focus back on the add button if it exists and triggered the modal
         if (event.relatedTarget === addSupplierBtn[0]) {
             addSupplierBtn.focus();
         } else if (event.relatedTarget && $(event.relatedTarget).is('#editSupplierBtn')) {
             // If the edit button triggered the edit modal, focus back on it
             $(event.relatedTarget).focus();
         } else {
             // Optionally focus on another relevant element if the trigger is not found
             // For example, you might want to focus on the main table or a specific section.
             // $('#yourMainTable').focus();
         }
     });
});