package com.cognixia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.cognixia.model.Product;

@RepositoryRestResource
public interface ProductRepository extends JpaRepository<Product, Integer>{
	
	List<Product> findByCategory(String category);
	
	List<Product> findByCode(String code);

	@Query("delete from Product p where p.rating=(select MIN(d.rating) from Product d)")
	@Modifying
	void deleteByMinRating();

	@Query("select p from Product p where p.rating=(select MAX(d.rating) from Product d)")
	@Modifying
	List<Product> findByMaxRating();
}
