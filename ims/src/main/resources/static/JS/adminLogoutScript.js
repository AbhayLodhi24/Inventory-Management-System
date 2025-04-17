$(document).ready(function () {
	// Handle Logout functionality
	   // Populate modal with user details when the logout button is clicked
	   $('#logoutButton').click(function (e) {
	       e.preventDefault(); // Prevent default action for the logout link
	       const loginName = "admin"; // Example user name
	       const loginEmail = "admin@gmail.com"; // Example user email
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
	       // Redirect to the login page or perform other logout actions
	       window.location.href = '/login'; // Replace with your actual login page
	   });
});