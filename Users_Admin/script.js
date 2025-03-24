$(document).ready(function(){
    //Search functionality
    $('#search').on('keyup', function() {
        const value = $(this).val().toLowerCase();
        $('tbody tr').filter(function() {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1);
        });
    });

    // Delete Row
    $(document).on('click', '.deleteButton', function () {
        $(this).closest('tr').remove();

        // Recalculate UIDs after deletion
        $(".uid").each(function (index) {
            $(this).text(index + 1);
        });
    });

    // Add new row
    $('#userForm').submit(function(event){
        event.preventDefault();
        const name = $('#name').val();
        const email = $('#mail').val();
        const pwd = $('#pwd').val();
        const address = $('#address').val();
        const role = $('#role').val();
        let uid = $('.uid').last().text();
        let new_uid = parseInt(uid)+1;
        if(!name || !email || !pwd || !address || !role){
            alert("Please fill all fields");
        }
        else{
            const newRow = `
            <tr>
                <td class="uid">${new_uid}</td>
                <td class="name">${name}</td>
                <td class="email">${email}</td>
                <td class="role">${role}</td>
                <td>
                    <button class="btn btn-link deleteButton text-danger btn-no-underline">Delete</button>
                </td>
            </tr>`;
            $('tbody').append(newRow);

            // Clear form inputs
            $("#userForm")[0].reset();
        }
    });


});