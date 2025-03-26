$(document).ready(function() {

      // Handle Logout functionality
    // Populate modal with user details when the logout button is clicked
    $('#logoutButton').click(function (e) {
      e.preventDefault(); // Prevent default action for the logout link
      const loginName = "admin"; // Example user name
      const loginEmail = "admin@gmail.com"; // Example user email
      $('#modalUserName').text(loginName); // Populate modal with user name
      $('#modalUserEmail').text(loginEmail); // Populate modal with user email
      console.log('Working');
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
      window.location.href = "../Login/login.html"; // Replace with your actual login page
  });
  // Dummy orders array
  let orders = [
    {
      sNo: 1,
      name: 'aril khan',
      address: 'main street',
      productName: 'Monitor',
      category: 'Electronic',
      quantity: 2,
      totalPrice: '$1000.00',
      orderDate: '3/18/2025'
    },
    {
      sNo: 2,
      name: 'Jane Smith',
      address: 'Green Avenue',
      productName: 'Keyboard',
      category: 'Electronic',
      quantity: 1,
      totalPrice: '$50.00',
      orderDate: '3/20/2025'
    },
    {
      sNo: 3,
      name: 'John Doe',
      address: 'Highway 21',
      productName: 'Desk',
      category: 'Furniture',
      quantity: 1,
      totalPrice: '$200.00',
      orderDate: '3/21/2025'
    }
  ];

  // Populate table
  orders.forEach(order => {
    let row = `
      <tr>
        <td>${order.sNo}</td>
        <td>${order.name}</td>
        <td>${order.address}</td>
        <td>${order.productName}</td>
        <td>${order.category}</td>
        <td>${order.quantity}</td>
        <td>${order.totalPrice}</td>
        <td>${order.orderDate}</td>
      </tr>
    `;
    $('#ordersTable tbody').append(row);
    });
  });
  