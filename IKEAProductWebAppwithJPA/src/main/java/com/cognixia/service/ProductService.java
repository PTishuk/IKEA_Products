package com.cognixia.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.cognixia.model.Product;
import com.cognixia.repository.ProductRepository;

@Service
@Transactional
public class ProductService {

	private static final int TAX = 13;
	private static final int DISCOUNT = 10;
	private static final String OUT_OF_STOCK = "The product is out of stock";
	private static final String CATEGORY_DISPLAY = "Category is: %s";
	private static final String TAX_TEMPLATE = "The product is %s. The cost of the product %s with %s%% tax is: %s$";
	private static final String DISCOUNT_TEMPLATE = "The product is %s with rating <<%s>>! The current discount on the product is %s%%! The cost of the product %s with discount is %s$";
	private DecimalFormat fr = new DecimalFormat("#.##");

	@Autowired
	ProductRepository pr;

	// Task 1. Product Detail: Print details of the Product
	public Product getById(int id) {
		return pr.findById(id).get();
	}

	// Task 1. Product Detail: Print details of the Product
	public List<Product> getAll() {
		return pr.findAll();
	}

	//Task 2. Product Filter: Based on the product category entered, print the name, code, rating and quantity using path variables.
	public List<String> getProductByCategory(String category) {
		List<String> filteredProducts = new ArrayList<String>();
		filteredProducts.add(String.format(CATEGORY_DISPLAY, category.toUpperCase()));
		for (Product p : pr.findByCategory(category)) {	
			if (p.getCategory().equalsIgnoreCase(category)) {
				filteredProducts.add(p.filtered());
			}
		}
		return filteredProducts;
	}

	//Task 3. Product creation: Add a new product
	public Product addProduct (Product product) {
		return pr.save(product);
	}

	//Task 4. Product Availability: Display the inStock and Quantity for the product code entered
	public List<String> getAvailableByCode(String code) {
		List<String> availableProducts = new ArrayList<String>();
		for (Product p : pr.findByCode(code)) {
			if (p.isInStock() && p.getQuantity()>0) {
				availableProducts.add(p.available());
			}
			else {
				availableProducts.add(OUT_OF_STOCK);
			}
		}
		return availableProducts;
	}

	//Task 5. Product Cost: Display the cost of the product along with 13% tax for a product category entered
	public List<String> displayCost(String category) {
		List<String> costByCategoryList = new ArrayList<String>();
		costByCategoryList.add(String.format(CATEGORY_DISPLAY, category.toUpperCase()));
		for (Product p : pr.findByCategory(category)) {
			double cost = p.getPrice() + p.getPrice()*TAX/100;
			costByCategoryList.add(String.format(TAX_TEMPLATE, p.getName(), p.getName(), TAX, fr.format(cost)));
		}
		return costByCategoryList;
	}

	//Task 6. Product Discount: Using a template, print a user friendly message for a 10% discount on the most popular product
	// OLDER VERSION
//	public List<String> displayDiscount() {
//		List<String> rated = new ArrayList<String>();
//		Integer[] arr = new Integer[pr.findAll().size()];
//		for (int i = 0; i<pr.findAll().size(); i++) {
//			arr[i] = pr.findAll().get(i).getRating();		
//		}
//		int max = Collections.max(Arrays.asList(arr));
//		for (Product p : pr.findAll()) {
//			if(p.getRating()==max) {
//				double cost = p.getPrice()-p.getPrice()*DISCOUNT/100;
//				rated.add(String.format(DISCOUNT_TEMPLATE, p.getName(), p.getRating(), DISCOUNT, p.getName(), fr.format(cost)));
//			}
//		}
//		return rated;
//	}
	
	//Task 6. Product Discount: Using a template, print a user friendly message for a 10% discount on the most popular product
	public List<String> displayDiscounted(){
		List<String> rated = new ArrayList<String>();
		List<Product> findRated = pr.findByMaxRating();
		for (int i=0; i<findRated.size(); i++) {
			double cost = findRated.get(i).getPrice()-findRated.get(i).getPrice()*DISCOUNT/100;
			rated.add(String.format(DISCOUNT_TEMPLATE, findRated.get(i).getName(), findRated.get(i).getRating(), DISCOUNT, findRated.get(i).getName(), fr.format(cost)));
		}
		return rated;
	}

	//Task 7. Product Update: Modify the Price of products for a product Id using request parameters.
	//ps.addProduct() method

	//Task 8. Product Purge: Delete products that have lowest rating.	
	public void deleteByRating() {
		pr.deleteByMinRating();
	}
}
