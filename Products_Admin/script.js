$(document).ready(function () {
    // Open Add Product Modal
    $('#addProductButton').click(function () {
        $('#modal').modal('show');
        $('#modalTitle').text('Add Product');
        $('#modalForm')[0].reset();
        $('#modalSubmitButton').text('Add').data('edit-mode', false);
    });

    // Open Edit Product Modal
    $(document).on('click', '.editButton', function () {
        $('#modal').modal('show');
        $('#modalTitle').text('Edit Product');
        $('#modalSubmitButton').text('Save').data('edit-mode', true);

        const row = $(this).closest('tr');
        $('#name').val(row.find('.name').text());
        $('#category').val(row.find('.category').text());
        $('#supplier').val(row.find('.supplier').text());
        $('#price').val(row.find('.price').text().replace('$', ''));
        $('#stock').val(row.find('.stock span').text());

        $('#modalForm').data('row', row);
    });

    // Delete Row
    $(document).on('click', '.deleteButton', function () {
        $(this).closest('tr').remove();
    });

    // Handle Form Submission (Add or Edit)
    $('#modalForm').submit(function (event) {
        event.preventDefault();
        const name = $('#name').val();
        const category = $('#category').val();
        const supplier = $('#supplier').val();
        const price = $('#price').val();
        const stock = $('#stock').val();
        const stockClass = stock > 10 ? 'badge badge-success' : stock > 0 ? 'badge badge-warning' : 'badge badge-danger';

        if ($('#modalSubmitButton').data('edit-mode')) {
            // Edit existing row
            const row = $('#modalForm').data('row');
            row.find('.name').text(name);
            row.find('.category').text(category);
            row.find('.supplier').text(supplier);
            row.find('.price').text(`${price}`);
            row.find('.stock span').text(stock).attr('class', stockClass);
        } else {
            // Add new row
            const newRow = `
                <tr>
                    <td class="name">${name}</td>
                    <td class="category">${category}</td>
                    <td class="supplier">${supplier}</td>
                    <td class="price">₹${price}</td>
                    <td class="stock"><span class="${stockClass}">${stock}</span></td>
                    <td>
                        <a href="#" class="text-primary mr-2 editButton">Edit</a>
                        <a href="#" class="text-danger deleteButton">Delete</a>
                    </td>
                </tr>
            `;
            $('tbody').append(newRow);
        }

        $('#modal').modal('hide'); // Close the modal
    });
});
