$(document).ready(function() {
    $('.sidebar li').click(function() {
        $('.sidebar li').removeClass('active');
        $(this).addClass('active');
        $('.content-section').hide();
        $('#' + $(this).data('content')).show();
    });

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
});