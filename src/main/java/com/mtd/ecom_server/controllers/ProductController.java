package com.mtd.ecom_server.controllers;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mtd.ecom_server.models.Product;
import com.mtd.ecom_server.repos.ProductRepo;

@CrossOrigin("*")
@RestController
@RequestMapping("/products")
public class ProductController {
	private static final Logger Log=LoggerFactory.getLogger(ProductController.class);
	@Autowired ProductRepo productRepo;
	
	@GetMapping("/all")
	public List<Product> getAllProducts() {
		Log.info("fetching products");
		return productRepo.findAll();
	}
	@PostMapping("/add")
	public Product addProduct(@RequestBody Product newproduct) {
		Log.info("Adding product"+" "+newproduct);
		return productRepo.save(newproduct);
	}
	@DeleteMapping("/product/delete/{id}")
	public String deleteProduct(@PathVariable String id) {
		Optional <Product> findproduct  = productRepo.findById(id);
		if(findproduct.isEmpty()) {
			productRepo.deleteById(id);
			Log.error("Failed to delete product"+id);
			return "Failed to delete product";
		}
		productRepo.deleteById(id);
		Log.info("Product delted" +id);
		return "Product deleted";
		
	}
	@PutMapping ("/product/edit/{id}")
	public Product editPorduct(@PathVariable String id, @RequestBody Product newproduct) {
		Product findproduct = productRepo.findById(id).get();
		findproduct.setName(newproduct.getName());
		findproduct.setDescription(newproduct.getDescription());
		findproduct.setCategory(newproduct.getCategory());
		findproduct.setTags(newproduct.getTags());
		findproduct.setPrice(newproduct.getPrice());
		findproduct.setStock(newproduct.getStock());
		return productRepo.save(findproduct) ;
	}
	
	
}
