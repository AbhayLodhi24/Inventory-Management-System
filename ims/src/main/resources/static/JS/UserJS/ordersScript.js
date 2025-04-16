//Orders JS

$(document).ready(function () {
    // Retrieve orders from sessionStorage
    const orders = JSON.parse(sessionStorage.getItem("orders")) || [];

    // Populate the Orders table
    const $tableBody = $("#ordersTableBody");
    if (orders.length === 0) {
        $tableBody.append(`<tr><td colspan="4" class="text-center">No orders found</td></tr>`);
    } else {
        orders.forEach(order => {
            const row = `
                <tr>
                    <td>${order.id}</td>
                    <td>${order.name}</td>
                    <td>${order.quantity}</td>
                    <td>${order.timestamp}</td>
                </tr>
            `;
            $tableBody.append(row);
        });
    }

   
});

 
 
 
 