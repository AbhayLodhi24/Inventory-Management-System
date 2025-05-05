$(document).ready(function () {
    // Category and Search filter functionality
    $('#categoryFilter').change(function () {
        filterTable();
    });
 
    $('#searchInput').on('input', function () {
        filterTable();
    });
 
    function filterTable() {
        var category = $('#categoryFilter').val().toLowerCase();
        var searchText = $('#searchInput').val().toLowerCase();
 
        $('#productTableBody tr').each(function () {
            var rowCategory = $(this).find('td:eq(1)').text().toLowerCase(); // Updated index for category
            var rowName = $(this).find('td:eq(0)').text().toLowerCase(); // Updated index for name
 
            if ((category === '' || rowCategory === category) &&
                (searchText === '' || rowName.includes(searchText))) {
                $(this).show();
            } else {
                $(this).hide();
            }
        });
    }
 
    // Open modal and set product details
    $(".order-btn").on("click", function () {
        const productName = $(this).data('product-name'); // Get product name
        const availableStock = $(this).data('product-stock');
        const categoryName = $(this).data('product-category-name'); // Get category name
 
        const $row = $(this).closest("tr");
        const stock = $row.find("td:nth-child(4)").text();
 
        $('#stockQuantity').val(stock);
        $('#quantity').val(1); // Default quantity
        $('#productName').val(productName); // Set product name in hidden field
        $('#categoryName').val(categoryName); // Set category name in hidden field
        $('#quantity').attr('max', availableStock); // Set max quantity
 
        $("#orderModal").modal("show"); // Show modal
    });
 
    // Handle Place Order form submission (no need for a separate button handler if using form submit)
    // The form will submit to the URL in th:action="@{/user/place-order}"
 
    $('#logoutButton').click(function (e) {
        e.preventDefault();
        const loginName = 'John Doe';
        const loginEmail = 'john.doe@example.com';
        $('#modalUserName').text(loginName);
        $('#modalUserEmail').text(loginEmail);
        $('#logoutModal').modal('show');
    });
 
    $('#cancelLogout').click(function () {
        $('#logoutModal').modal('hide');
    });
 
    $('#confirmLogout').click(function () {
        $('#logoutModal').modal('hide');
        window.location.href = '../../Admin/Login/login.html';
    });
});