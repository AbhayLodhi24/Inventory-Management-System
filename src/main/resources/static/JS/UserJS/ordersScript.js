$(document).ready(function () {
    const $tableBody = $("#ordersTableBody");
 
    if ($tableBody.children().length === 0) {
        $tableBody.append(`<tr><td colspan="5" class="text-center">No orders found</td></tr>`);
    }
});