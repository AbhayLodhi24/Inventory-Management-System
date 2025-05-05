package com.project.ims.Service;
 
import java.time.LocalDateTime;
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import com.project.ims.Model.Products;
import com.project.ims.Repository.UserRepository;
 
import jakarta.transaction.Transactional;
 
import com.project.ims.Repository.CategoryRepository;
import com.project.ims.Repository.ProductRepository;
import com.project.ims.Repository.OrdersRepository;
import com.project.ims.Model.*;
 
@Service
public class UserOrderService {
 
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserProductService productService;
 
    @Autowired
    private CategoryRepository categoryRepository;
 
    @Autowired
    private ProductRepository productRepository;
 
    @Autowired
    private OrdersRepository orderRepository;
 
 
    public List<Orders> getOrdersByUserId(Long userId) {
        return orderRepository.findByUser_UserId(userId);
    }
 
 
    @Transactional
    public void placeNewOrder(Integer quantity, String productName, String categoryName, Long userId) {
        // 1. Fetch the User
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with Id : " + userId));
 
        // 2. Fetch the Product to get the price
        Products product = productService.findByName(productName);
        if (product == null) {
            throw new RuntimeException("Product not found with name: " + productName);
        }
        float totalPrice = product.getPrice() * quantity;
 
        // 3. Create a new Orders object
        Orders order = new Orders();
        order.setQuantity(quantity);
        order.setTotalPrice(totalPrice);
        order.setOrderDate(LocalDateTime.now());
        order.setUser(user);
        order.setProductName(productName);
        order.setCategoryName(categoryName);
 
        // 4. Save the order to the database
        orderRepository.save(order);
 
        // 5. Optionally update the product stock (consider potential race conditions)
        int newStock = product.getStock() - quantity;
        if (newStock >= 0) {
            product.setStock(newStock);
            productService.saveProduct(product); // Use productService to save the updated product
        } else {
            throw new RuntimeException("Insufficient stock for product: " + productName);
        }
    }
}