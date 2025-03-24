$(document).ready(function () {
    // Array to store categories
    let categories = [
        { id: 1, name: "Tech", description: "Technology-related items" },
        { id: 2, name: "Fashion", description: "Fashionable clothing and accessories" },
        { id: 3, name: "Electronic", description: "Electronic gadgets and devices" }
    ];

    // Function to render table
    function renderTable() {
        let tbody = $("tbody");
        tbody.empty(); // Clear current table rows
        categories.forEach(category => {
            let row = `
            <tr data-id="${category.id}">
                <td>${category.id}</td>
                <td class="category-name">${category.name}</td>
                <td>
                    <button class="btn btn-edit btn-sm edit-btn">Edit</button>
                    <button class="btn btn-delete btn-sm delete-btn">Delete</button>
                </td>
            </tr>
        `;
            tbody.append(row);
        });
    }

    // Render the table on page load
    renderTable();

    // Edit button functionality
    $(document).on("click", ".edit-btn", function () {
        let row = $(this).closest("tr");
        let categoryId = row.data("id");
        let category = categories.find(cat => cat.id === categoryId);
        $("#category-name").val(category.name);
        $("#category-description").val(category.description);
        $("#edit-category-form").data("categoryId", categoryId);
    });

    // Save changes to the category
    $("#edit-category-form").submit(function (e) {
        e.preventDefault();
        let categoryId = $(this).data("categoryId");
        let newName = $("#category-name").val();
        let newDescription = $("#category-description").val();
        let category = categories.find(cat => cat.id === categoryId);
        if (category) {
            category.name = newName; // Update the name in the array
            category.description = newDescription; // Update the description in the array
            renderTable(); // Re-render the table
        }
    });

    // Delete button functionality
    $(document).on("click", ".delete-btn", function () {
        let row = $(this).closest("tr");
        let categoryId = row.data("id");
        categories = categories.filter(cat => cat.id !== categoryId); // Remove from array
        renderTable(); // Re-render the table
    });

    // Cancel button functionality
    $("#cancel-btn").click(function () {
        $("#edit-category-form")[0].reset();
    });

    // Search functionality
    $("#search-input").on("keyup", function () {
        let value = $(this).val().toLowerCase();
        $("tbody tr").filter(function () {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1);
        });
    });
});