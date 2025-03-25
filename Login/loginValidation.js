$(document).ready(function() {
    $('input[name="role"]').change(function() {
        if ($('#admin-radio').is(':checked')) {
            $('#username').attr('placeholder', 'Admin Username');
            $('#register-link').hide();
        } else {
            $('#register-link').show();
            $('#username').attr('placeholder', 'Username');
        }
    });

    $('#register-link').click(function(e) {
        e.preventDefault();
        $('#form-title').text('Register');
        $('#login-form').html(`
            <div class="mb-3">
                <input type="text" class="form-control" id="username" placeholder="Username">
            </div>
            <div class="mb-3">
                <input type="email" class="form-control" id="email" placeholder="Email">
            </div>
            <div class="mb-3">
                <input type="password" class="form-control" id="password" placeholder="Password">
            </div>
            <button type="submit" class="btn-green">Register</button>
        `);
    });
});