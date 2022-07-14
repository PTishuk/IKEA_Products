package com.cognixia.controller;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cognixia.model.Product;
import com.cognixia.service.ProductService;

@RestController
@RequestMapping("/ikea/products")
public class ProductController {

	private static final String ERROR = "ERROR";
	private static final String PRODUCT_NOT_FOUND = "There is no such product in database";

	@Autowired
	private ProductService ps;

	// Task 1. Product Detail: Print details of the Product
	@GetMapping("/{id}")
	public ResponseEntity<Product> displayDetails(@PathVariable int id) {
		try {
			return ResponseEntity.ok(ps.getById(id));
		}
		catch (NoSuchElementException e) {
			return ResponseEntity.notFound().header(ERROR, PRODUCT_NOT_FOUND).build();
		}
	}

	@GetMapping("/")
	public List<Product> displayDetails() {
		return ps.getAll();
	}

	//Task 2. Product Filter: Based on the product category entered, print the name, code, rating and quantity using path variables.
	@GetMapping("/category/{category}")
	public List<String> displayFilter(@PathVariable String category) {
		return ps.getProductByCategory(category);
	}	

	//Task 3. Product creation: Add a new product
	@PostMapping("/")
	public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
		try {
			ps.addProduct(product);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(ps.addProduct(product).getId()).toUri();
			return ResponseEntity.created(location).build();
		}
		catch(Exception e) {
			return ResponseEntity.noContent().build();
		}
	}

	//Task 4. Product Availability: Display the inStock and Quantity for the product code entered
	@GetMapping("/available/{code}")
	public List<String> getAvailable(@PathVariable String code) {		
		return ps.getAvailableByCode(code);
	}

	//Task 5. Product Cost: Display the cost of the product along with 13% tax for a product category entered
	@GetMapping("/cost/{category}")
	public List<String> displayCost(@PathVariable String category) {
		return ps.displayCost(category);	
	}

	//Task 6. Product Discount: Using a template, print a user friendly message for a 10% discount on the most popular product
	@GetMapping("/discount")
	public List<String> displayDiscount() {
		return ps.displayDiscounted();
	}

	//Task 7. Product Update: Modify the Price of products for a product Id using request parameters.
	@PutMapping("/")
	public ResponseEntity<Product> updateProduct(@RequestParam Integer id, @RequestParam Double price) {
		try	{
			Product updatedProduct = ps.getById(id);
			if (updatedProduct != null) {
				updatedProduct.setPrice(price);
				ps.addProduct(updatedProduct);
			}
			return ResponseEntity.ok(ps.addProduct(updatedProduct));
		} 
		catch (NoSuchElementException e) {
			return ResponseEntity.notFound().header(ERROR, PRODUCT_NOT_FOUND).build();
		}
	}

	//Task 8. Product Purge: Delete products that have lowest rating.
	@DeleteMapping("/")
	public void delete() {
		ps.deleteByRating();
	}
}
