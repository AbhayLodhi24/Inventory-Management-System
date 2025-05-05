$(document).ready(function () {
// Handle Logout functionality
   // Populate modal with user details when the logout button is clicked
   			   const logoutModalElement = $('#logoutModal');
               const logoutButton = $('#logoutButton');
               const cancelLogoutButton = $('#cancelLogout');
               const logoutForm = $('#logoutForm');
               const confirmLogoutButton = $('#confirmLogout');
  logoutButton.click(function (e) {
           e.preventDefault(); // Prevent default action for the logout link
          

           // Show the logout confirmation modal
           $('#logoutModal').modal('show');
       });

   // Cancel Logout Functionality
   cancelLogoutButton.click(function ()  {
       $('#logoutModal').modal('hide'); // Simply hide the modal without any further action
   });

   // Handle "Logout" confirmation in the modal
   confirmLogoutButton.click(function () {
                  logoutForm.submit(); // Submit the Spring Security logout form
              });
   
  });