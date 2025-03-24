$(document).ready(function () {
    // Sample Data (Can be replaced with API data)
    const stockData = [
        {
            title: "Out of Stock Products",
            content: "Screen (Electronic)"
        },
        {
            title: "Highest Sale Product",
            content: `
                Name: Monitor <br>
                Category: Electronic <br>
                Total Units Sold: 2
            `
        },
        {
            title: "Low Stock Products",
            content: `
                Monitor - 3 left (Electronic) <br>
                PC - 3 left (Tech) <br>
                RAM - 2 left (Tech)
            `
        }
    ];

    // Render Cards Dynamically
    stockData.forEach((item) => {
        $("#stock-info").append(`
            <div class="col-md-4">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">${item.title}</h5>
                        <p>${item.content}</p>
                    </div>
                </div>
            </div>
        `);
    });
});
