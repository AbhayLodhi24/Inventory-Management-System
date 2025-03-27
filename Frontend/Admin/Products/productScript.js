$(document).ready(function () {

    // Sidebar loading...
    // $(".sidebar-container").on("load", function() {
    //     "../admin_Sidebar/index.html";
    // });

    // Handle Logout functionality
    // Populate modal with user details when the logout button is clicked
    $('#logoutButton').click(function (e) {
        e.preventDefault(); // Prevent default action for the logout link
        const loginName = "admin"; // Example user name
        const loginEmail = "admin@gmail.com"; // Example user email
        $('#modalUserName').text(loginName); // Populate modal with user name
        $('#modalUserEmail').text(loginEmail); // Populate modal with user email
        console.log('Working');
        // Show the logout confirmation modal
        $('#logoutModal').modal('show');
    });
 
    // Cancel Logout Functionality
    $('#cancelLogout').click(function () {
        $('#logoutModal').modal('hide'); // Simply hide the modal without any further action
    });
 
    // Handle "Logout" confirmation in the modal
    $('#confirmLogout').click(function () {
        $('#logoutModal').modal('hide'); // Hide the modal
        window.location.href = "../Login/login.html"; // Replace with your actual login page
    });
    
    //Search functionality
    $("#search").on('input', function () {
        var searchText = $('#search').val().toLowerCase();
        $("tbody tr").each(function() {                  
            var rowName = $(this).find('td:eq(1)').text().toLowerCase();  
            if ( searchText === '' || rowName.includes(searchText)) {  
                $(this).show();
            } else {
                $(this).hide();  
            }  
        });
    });$('#search').on('keyup', function() {
        const value = $(this).val().toLowerCase();
        $('tbody tr').filter(function() {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1);
        });
    });

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
                        <button class="btn btn-primary editButton btn-no-underline mr-2" style="width: 40%;">Edit</button>
                        <button class="btn btn-danger deleteButton btn-no-underline">Delete</button>
                    </td>
                </tr>
            `;
            $('tbody').append(newRow);
        }

        $('#modal').modal('hide'); // Close the modal
    });
});
