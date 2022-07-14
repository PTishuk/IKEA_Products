package com.cognixia.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Products")
public class Product {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	private int id;
	@Column(name="Name")
	private String name;
	@Column(name="Code")
	private String code;
	@Column(name="Category")
	private String category;
	@Column(name="Price")
	private double price;
	@Column(name="Quantity")
	private int quantity;
	@Column(name="Available")
	private boolean inStock;
	@Column(name="Rating")
	private int rating;

	public Product() {
		super();
	}

	public Product(int id, String name, String code, String category, double price, int quantity, boolean inStock, int rating) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.category = category;
		this.price = price;
		this.quantity = quantity;
		this.inStock = inStock;
		this.rating = rating;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public boolean isInStock() {
		return inStock;
	}
	public void setInStock(boolean inStock) {
		this.inStock = inStock;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}

	public String available() {
		return "Profuct name is: " + name + " | Product is in stock: " + isInStock() + " | Product quantity in stock is: " + quantity;
	}
	public String filtered() {
		return "Product name is :" + name + " | Product code is: " + code + " | Product quantity in stock is: " + quantity + " | Product rating is: " + rating;
	}
}
