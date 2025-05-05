$(document).ready(function() {
    // 1. DOM Element Variables (Constants)
    const addProductModal = $('#addProductModal');
    const addProductForm = $('#addProductForm');
    const addProductModalLabel = $('#addProductModalLabel');
    const editProductModalLabel = $('#editProductModalLabel');
    const productIdInput = $('#newProductId'); // Correctly selecting your hidden input
	productIdInput.val(0);
    // 2. Global Variable(s)
    let existingProductDetails = []; // Store fetched product details

    // 3. Utility Function(s)
    function updateErrorDisplay(fieldId, errorMessage) {
        const inputElement = $('#' + fieldId);
        if (errorMessage) {
            inputElement.addClass('is-invalid');
            alert(errorMessage);
        } else {
            inputElement.removeClass('is-invalid');
        }
    }

    // 4. Functions for Specific Actions (like fetching data)
    function fetchExistingProductDetails() {
        return $.ajax({
            url: '/admin/products/existingNames', // Now returns details with productId
            type: 'GET'
        });
    }

    function submitFormData(formData, url) {
        $.ajax({
            url: url,
            type: 'POST',
            data: formData,
            success: function(response) {
                addProductModal.modal('hide');
                window.location.reload();
            },
            error: function(xhr, status, error) {
                handleServerError(xhr);
            }
        });
    }

    function handleServerError(xhr) {
        if (xhr.status === 400) {
            const tempDiv = $('<div>').html(xhr.responseText);
            $('#name').val(tempDiv.find('#name').val());
            $('#price').val(tempDiv.find('#price').val());
            $('#stock').val(tempDiv.find('#stock').val());
            $('#categoryId').val(tempDiv.find('#categoryId').val());
            $('#supplierId').val(tempDiv.find('#supplierId').val());
            const generalError = tempDiv.find('.alert.alert-danger');
            if (generalError.length) {
                alert(generalError.text());
            }
            // Keep the modal open by NOT calling addProductModal.modal('hide');
        } else {
            alert("An error occurred while adding/editing the product.");
            console.error("Error adding/editing product:", error);
        }
    }

    // 5. Event Handlers (in the order they are likely to be triggered)

    // When an "Edit" button is clicked
    $('.edit-btn').on('click', function() {
        const productId = $(this).data('product-id');
        const productName = $(this).data('product-name');
        const categoryId = $(this).data('category-id');
        const supplierId = $(this).data('supplier-id');
        const productPrice = $(this).data('product-price');
        const productStock = $(this).data('product-stock');

        addProductModalLabel.addClass('d-none'); // Hide "Add New Product" title
        editProductModalLabel.removeClass('d-none'); // Show "Edit Product" title

        productIdInput.val(productId);
        $('#name').val(productName);
        $('#categoryId').val(categoryId);
        $('#supplierId').val(supplierId);
        $('#price').val(productPrice);
        $('#stock').val(productStock);

        console.log("Editing Product ID:", productIdInput.val());
    });

    // When the "Add/Edit Product" modal is shown
    addProductModal.on('show.bs.modal', function() {
        console.log("Product ID on show modal:", productIdInput.val());
        fetchExistingProductDetails().done(function(data) {
            existingProductDetails = data;
        }).fail(function(error) {
            console.error("Error fetching existing product details:", error);
        });

        addProductForm.find('.is-invalid').removeClass('is-invalid');
    });

    // When the "Add/Edit Product" modal is hidden
    addProductModal.on('hidden.bs.modal', function () {
        console.log("addProductModal hidden - resetting productId");
        addProductModalLabel.removeClass('d-none');
        editProductModalLabel.addClass('d-none');
        addProductForm[0].reset();
        addProductForm.find('.is-invalid').removeClass('is-invalid');
        productIdInput.val(0);
        addProductForm.attr('action', '/admin/products/addProduct');
    });

    // When the "Add/Edit Product" form is submitted
    addProductForm.submit(function(event) {
        event.preventDefault(); // Prevent default form submission
        console.log("Product ID on submit:", productIdInput.val()); // ADD THIS LINE
        let shouldSubmit = true; // Flag to control submission

        // Client-side validation based on ProductDto constraints
        if ($('#name').val().trim() === "") {
            updateErrorDisplay('name', 'Product name is required.');
            shouldSubmit = false;
        } else if ($('#name').val().trim().length > 255) {
            updateErrorDisplay('name', 'Product name cannot exceed 255 characters.');
            shouldSubmit = false;
        } else {
            updateErrorDisplay('name', '');
        }

        if ($('#categoryId').val() === "") {
            updateErrorDisplay('categoryId', 'Category is required.');
            shouldSubmit = false;
        } else {
            updateErrorDisplay('categoryId', '');
        }

        if ($('#supplierId').val() === "") {
            updateErrorDisplay('supplierId', 'Supplier is required.');
            shouldSubmit = false;
        } else {
            updateErrorDisplay('supplierId', '');
        }

        const priceValue = parseFloat($('#price').val());
        if (isNaN(priceValue)) {
            updateErrorDisplay('price', 'Price is required and must be a valid number.');
            shouldSubmit = false;
        } else if (priceValue < 0) {
            updateErrorDisplay('price', 'Price cannot be negative.');
            shouldSubmit = false;
        } else if (priceValue > 10000000) {
            updateErrorDisplay('price', 'Price cannot exceed 10,000,000.');
        } else {
            updateErrorDisplay('price', '');
        }

        const stockValue = $('#stock').val().trim(); // Get the raw value
        const parsedStock = parseInt(stockValue);
        if (stockValue === "") {
            updateErrorDisplay('stock', 'Stock is required.');
            shouldSubmit = false;
        } else if (isNaN(parsedStock) || parsedStock < 0 || parsedStock != stockValue) {
            updateErrorDisplay('stock', 'Stock must be a valid non-negative integer.');
            shouldSubmit = false;
        } else if (parsedStock > 1000000) {
            updateErrorDisplay('stock', 'Stock cannot exceed 1,000,000.');
        } else {
            updateErrorDisplay('stock', '');
        }

        if (shouldSubmit) {
            const formData = $(this).serialize();
            const productName = $('#name').val().trim();
            const categoryId = parseInt($('#categoryId').val());
            const supplierId = parseInt($('#supplierId').val());

            let isExactDuplicate = false;
            if (typeof existingProductDetails !== 'undefined') {
                for (const product of existingProductDetails) {
                    // Only check for duplicates if the current product is NOT the one being edited
                    if (parseInt(product.productId) !== parseInt(productIdInput.val())) {
                        if (product.name === productName &&
                            parseInt(product.categoryId) === categoryId &&
                            parseInt(product.supplierId) === supplierId) {
                            isExactDuplicate = true;
                            console.log('Same ids');
                            break;
                        }
                    } else {
                        console.log('Not Same ids');
                    }
                }
            }

            if (isExactDuplicate) {
                alert("A product with the same name, category, and supplier already exists. So you can't save this product.");
            } else {
                const actionUrl = productIdInput.val() === '0' ? '/admin/products/addProduct' : '/admin/products/editProduct';
                addProductForm.attr('action', actionUrl);
                submitFormData(formData, actionUrl);
            }
        }
    });
});