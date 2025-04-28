package com.project.ims.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.ims.Model.Products;
import com.project.ims.Model.Supplier;
import com.project.ims.Model.SupplierDto;
import com.project.ims.Repository.CategoryRepository;
import com.project.ims.Repository.ProductRepository;
import com.project.ims.Repository.SupplierRepository;
import com.project.ims.Service.CategoryService;
import com.project.ims.Service.SupplierService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	ProductRepository productRepository;

	@Autowired
	SupplierRepository supplierRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	CategoryService categoryService;

	@Autowired
	private SupplierService supplierService;

	@GetMapping("/dashboard")
	public String getDashboardPage(Model model) {

		return "/Frontend/Admin/Dashboard/dashboard";
	}

	@GetMapping("/order")
	public String getOrderPage(Model model) {

		return "/Frontend/Admin/Orders/order";
	}

	@GetMapping("/product")
	public String getProductPage(Model model) {
		List<Products> products = productRepository.findAll();
        model.addAttribute("products", products);
		return "/Frontend/Admin/Products/product";
	}

//	@GetMapping("/categories")
//    public String getCategoriesPage(@RequestParam(value = "query", required = false, defaultValue = "") String query,
//                                    @RequestParam(value = "page", required = false, defaultValue = "0") int page,
//                                    @RequestParam(value = "size", required = false, defaultValue = "5") int size,
//                                    Model model) {
//        Pageable pageable = PageRequest.of(page, size);
//        Page<Category> categoryPage = categoryService.getAllCategories(query, pageable);
//        model.addAttribute("categoriesList", categoryPage.getContent());
//        model.addAttribute("currentPage", categoryPage.getNumber());
//        model.addAttribute("totalPages", categoryPage.getTotalPages());
//        model.addAttribute("totalItems", categoryPage.getTotalElements());
//        model.addAttribute("query", query);
//        model.addAttribute("cat", new CategoryDto()); // For the add/edit modal form
//        return "/Frontend/Admin/Categories/categories";
//    }

	@GetMapping("/suppliers")
	public String getSuppliersPage(@RequestParam(value = "query", required = false, defaultValue = "") String query,
								   @RequestParam(value = "page", required = false, defaultValue = "0") int page,
								   @RequestParam(value = "size", required = false, defaultValue = "5") int size,
								   Model model){

		Page<Supplier> suppliers = supplierService.getAllSuppliers(	query,PageRequest.of(page, size));
		model.addAttribute("suppliersList", suppliers.getContent());
		model.addAttribute("totalPages",suppliers.getTotalPages());
		model.addAttribute("query",query);
		SupplierDto supplierDTO = new SupplierDto();
		model.addAttribute("supp", supplierDTO);
		model.addAttribute("suppliers", supplierService.getAllSuppliersFromTable());
		return "/Frontend/Admin/Suppliers/suppliers";
	}


	@GetMapping("/users")
	public String getUsersPage(Model model) {

		return "/Frontend/Admin/Users/users";
	}

	@GetMapping("/profile")
	public String getProfilePage(Model model) {

		return "/Frontend/Admin/admin_profile/admin";
	}
}