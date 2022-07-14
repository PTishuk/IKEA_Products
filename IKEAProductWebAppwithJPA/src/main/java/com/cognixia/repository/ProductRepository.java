package com.cognixia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.cognixia.model.Product;

@RepositoryRestResource
public interface ProductRepository extends JpaRepository<Product, Integer>{
	
	List<Product> findByCategory(String category);
	
	List<Product> findByCode(String code);
}
