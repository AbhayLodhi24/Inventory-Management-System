$(document).ready(function () {
    // Sidebar navigation
   // $('.sidebar li').click(function() {
    // $('.sidebar li').removeClass('active');
    // $(this).addClass('active');
    // $('.content-section').hide();
    // $('#' + $(this).data('content')).show();
    // });
    
    // Category and Search filter functionality  
    $('#categoryFilter').change(function() {  
        filterTable();  
    });  
    
    $('#searchInput').on('input', function() {  
        filterTable();  
    });  
    
    function filterTable() {  
        var category = $('#categoryFilter').val().toLowerCase();  
        var searchText = $('#searchInput').val().toLowerCase();  
    
        $('#productTableBody tr').each(function() {  
            var rowCategory = $(this).find('td:eq(2)').text().toLowerCase();  
            var rowName = $(this).find('td:eq(1)').text().toLowerCase();  
    
            if ((category === '' || rowCategory === category) &&  
                (searchText === '' || rowName.includes(searchText))) {  
                $(this).show();  
            } else {  
                $(this).hide();  
            }  
        });  
    }  
    
    // Open modal and set stock value  
    $(".order-btn").on("click", function () {  
        const $row = $(this).closest("tr");  
        const productId = $row.find("td:nth-child(1)").text(); // Get product ID  
        const productName = $row.find("td:nth-child(2)").text(); // Get product Name  
        const stock = $row.find("td:nth-child(5)").text(); // Get stock value  
    
        $("#stockQuantity").val(stock);  
        $("#orderQuantity").val(0);  
        $("#orderModal").data("productId", productId); // Save product ID in modal  
        $("#orderModal").data("productName", productName); // Save product Name in modal  
        $("#orderModal").modal("show"); // Show modal  
    });  
    
    // Handle Place Order button click  
    $("#placeOrderButton").on("click", function () {  
        const orderQuantity = parseInt($("#orderQuantity").val(), 10);  
        const stockQuantity = parseInt($("#stockQuantity").val(), 10);  
        const productId = $("#orderModal").data("productId"); // Retrieve product ID from modal  
        const productName = $("#orderModal").data("productName"); // Retrieve product Name from modal  
    
        if (orderQuantity > 0 && orderQuantity <= stockQuantity) {  
            // Retrieve existing orders from sessionStorage or initialize an empty array  
            let orders = JSON.parse(sessionStorage.getItem("orders")) || [];  
    
            // Add new order details  
            orders.push({  
                id: productId,  
                name: productName,  
                quantity: orderQuantity,  
                timestamp: new Date().toLocaleString()  
            });  
    
            // Save updated orders back to sessionStorage  
            sessionStorage.setItem("orders", JSON.stringify(orders));  
    
            alert("Order placed successfully!");  
            $("#orderModal").modal("hide"); // Hide the modal  
        } else {  
            alert("Invalid order quantity! Please ensure it's less than or equal to the available stock.");  
        }  
    });  
    
    
    

    $('#logoutButton').click(function (e) {  
        e.preventDefault();  // Prevent default action for the logout link  
        const loginName = 'user1';  
        const loginEmail = 'user1@gmail.com';  
        $('#modalUserName').text(loginName); // Populate modal with user name  
        $('#modalUserEmail').text(loginEmail); // Populate modal with user email  
    
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
        console.log("User has logged out."); // Log the action  
         // Notify the user (can be replaced with actual logout logic)  
        // Redirect to the login page or perform other logout actions  
        window.location.href = '../Login/login.html'; // Replace with your actual login page  
    });
});