package com.miniproject.service;

import java.util.List;

import com.miniproject.exception.ProductException;
import com.miniproject.model.Product;



public interface ProductService {
	
	public List<Product> viewAllProducts() throws ProductException;
		
	public Product addProduct(Product product) throws ProductException;
	public Product updateProduct(Product product) throws ProductException;
	public Product viewProduct(Integer id) throws ProductException;
	public List<Product> viewProductByCategory(String cname) throws ProductException;
	public Product removeProduct(Integer pid)throws ProductException;

}
