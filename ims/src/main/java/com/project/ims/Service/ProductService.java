package com.project.ims.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.ims.Model.Products;
import com.project.ims.Repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	public List<Products> getAllProducts(){
		List<Products> products = productRepository.findAll();
		return products;
	}
	
}
