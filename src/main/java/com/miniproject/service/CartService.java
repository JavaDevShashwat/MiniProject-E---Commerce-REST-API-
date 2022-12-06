package com.miniproject.service;

import java.util.List;

import javax.security.auth.login.LoginException;

import com.miniproject.exception.CartException;
import com.miniproject.exception.ProductException;
import com.miniproject.model.Cart;
import com.miniproject.model.Product;



public interface CartService {

	
	public Cart addProductToCart(Integer productId, int quantity,String key) throws CartException, LoginException, ProductException ;
	
	public List<Product> removeProductFromCart(Integer productId,String key) throws CartException, ProductException, LoginException  ;
	
	public List<Product> updateProductQuantity(Integer productId,Integer quantity,String key) throws CartException, LoginException, ProductException ;
	
	public Cart removeAllProducts(String key) throws CartException, LoginException ;
	
	public List<Product> viewAllProducts(String key)  throws CartException, LoginException;
 
	

}
