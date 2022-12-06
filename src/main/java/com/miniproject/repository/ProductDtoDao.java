package com.miniproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.miniproject.model.Product;


public interface ProductDtoDao extends JpaRepository<Product, Integer> {

	
	
}
