$(document).ready(function() {
    // 1. DOM Element Variables (Constants)
    const addProductModal = $('#addProductModal');
    const addProductForm = $('#addProductForm');
    const addProductModalLabel = $('#addProductModalLabel');
    const editProductModalLabel = $('#editProductModalLabel');
    const productIdInput = $('#newProductId');
    productIdInput.val(0);

    // 2. Global Variable(s)
    let existingProductDetails = [];

    // 3. Utility Function(s)
    function updateErrorDisplay(fieldId, errorMessage) {
        const inputElement = $('#' + fieldId);
        const errorElement = $('#' + fieldId + 'Error');

        if (errorMessage) {
            inputElement.addClass('is-invalid');
            errorElement.text(errorMessage);
        } else {
            inputElement.removeClass('is-invalid');
            errorElement.text('');
        }
    }

    // 4. Functions for Specific Actions
    function fetchExistingProductDetails() {
        return $.ajax({
            url: '/admin/products/existingNames',
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
        } else {
            alert("An error occurred while adding/editing the product.");
            console.error("Error adding/editing product:", error);
        }
    }

    // 5. Event Handlers

    $('.edit-btn').on('click', function() {
        const productId = $(this).data('product-id');
        const productName = $(this).data('product-name');
        const categoryId = $(this).data('category-id');
        const supplierId = $(this).data('supplier-id');
        const productPrice = $(this).data('product-price');
        const productStock = $(this).data('product-stock');

        addProductModalLabel.addClass('d-none');
        editProductModalLabel.removeClass('d-none');

        productIdInput.val(productId);
        $('#name').val(productName);
        $('#categoryId').val(categoryId);
        $('#supplierId').val(supplierId);
        $('#price').val(productPrice);
        $('#stock').val(productStock);
    });

    addProductModal.on('show.bs.modal', function() {
        fetchExistingProductDetails().done(function(data) {
            existingProductDetails = data;
        }).fail(function(error) {
            console.error("Error fetching existing product details:", error);
        });
        addProductForm.find('.is-invalid').removeClass('is-invalid');
        addProductForm.find('.invalid-feedback').text('');
    });

    addProductModal.on('hidden.bs.modal', function () {
        console.log("addProductModal hidden - resetting productId");
        addProductModalLabel.removeClass('d-none');
        editProductModalLabel.addClass('d-none');
        addProductForm[0].reset();
        addProductForm.find('.is-invalid').removeClass('is-invalid');
        productIdInput.val(0);
        addProductForm.attr('action', '/admin/products/addProduct');
    });

    // Real-time validation on input event
    $('#name').on('input', function() {
        const nameValue = $(this).val().trim();
        if (nameValue === "") {
            updateErrorDisplay('name', 'Product name is required.');
        } else if (nameValue.length < 4 || nameValue.length > 255) {
            updateErrorDisplay('name', 'Product name should be between 4 to 255 characters.');
        } else {
            updateErrorDisplay('name', '');
        }
    });

    $('#price').on('input', function() {
        const priceValue = parseFloat($(this).val());
        if (isNaN(priceValue)) {
            updateErrorDisplay('price', 'Price is required and must be a valid number.');
        } else if (priceValue < 0) {
            updateErrorDisplay('price', 'Price cannot be negative.');
        } else if (priceValue > 10000000) {
            updateErrorDisplay('price', 'Price cannot exceed 10,000,000.');
        } else {
            updateErrorDisplay('price', '');
        }
    });

    $('#stock').on('input', function() {
        const stockValue = $(this).val().trim();
        const parsedStock = parseInt(stockValue);
        if (stockValue === "") {
            updateErrorDisplay('stock', 'Stock is required.');
        } else if (isNaN(parsedStock) || parsedStock < 0 || parsedStock != stockValue) {
            updateErrorDisplay('stock', 'Stock must be a valid non-negative integer.');
        } else if (parsedStock > 1000000) {
            updateErrorDisplay('stock', 'Stock cannot exceed 1,000,000.');
        } else {
            updateErrorDisplay('stock', '');
        }
    });

    $('#categoryId').on('change', function() {
        if ($(this).val() === "") {
            updateErrorDisplay('categoryId', 'Category is required.');
        } else {
            updateErrorDisplay('categoryId', '');
        }
    });

    $('#supplierId').on('change', function() {
        if ($(this).val() === "") {
            updateErrorDisplay('supplierId', 'Supplier is required.');
        } else {
            updateErrorDisplay('supplierId', '');
        }
    });

    // When the "Add/Edit Product" form is submitted
    addProductForm.submit(function(event) {
        event.preventDefault();
        let shouldSubmit = true;

        // Trigger all validation checks on submit
        $('#name').trigger('input');
        $('#price').trigger('input');
        $('#stock').trigger('input');
        $('#categoryId').trigger('change');
        $('#supplierId').trigger('change');

        if (addProductForm.find('.is-invalid').length > 0) {
            shouldSubmit = false;
        }

        if (shouldSubmit) {
            const formData = $(this).serialize();
            const productName = $('#name').val().trim();
            const categoryId = parseInt($('#categoryId').val());
            const supplierId = parseInt($('#supplierId').val());

            let isExactDuplicate = false;
            if (typeof existingProductDetails !== 'undefined') {
                for (const product of existingProductDetails) {
                    if (parseInt(product.productId) !== parseInt(productIdInput.val())) {
                        if (product.name === productName &&
                            parseInt(product.categoryId) === categoryId &&
                            parseInt(product.supplierId) === supplierId) {
                            isExactDuplicate = true;
                            break;
                        }
                    }
                }
            }

            if (isExactDuplicate) {
                //alert("A product with the same name, category, and supplier already exists. So you can't save this product.");
				updateErrorDisplay('name', 'A product with the same name, category, and supplier already exists.');
				shouldSubmit = false;
            } else {
                const actionUrl = productIdInput.val() === '0' ? '/admin/products/addProduct' : '/admin/products/editProduct';
                addProductForm.attr('action', actionUrl);
                submitFormData(formData, actionUrl);
            }
        }
    });
});