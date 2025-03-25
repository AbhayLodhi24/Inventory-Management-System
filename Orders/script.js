$(document).ready(function() {
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
  