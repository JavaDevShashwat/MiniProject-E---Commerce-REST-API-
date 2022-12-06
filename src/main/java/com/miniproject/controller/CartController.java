package com.miniproject.controller;

import java.util.List;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.miniproject.exception.CartException;
import com.miniproject.exception.ProductException;
import com.miniproject.model.Cart;
import com.miniproject.model.Product;
import com.miniproject.repository.ProductDtoDao;
import com.miniproject.service.CartService;




@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;




    @PostMapping("/add")
    public ResponseEntity<Cart> addToCart(@RequestParam Integer productId , 
    									  @RequestParam Integer quantity,
    									  @RequestParam String key) throws CartException, LoginException, ProductException{
    	
    	

        

   


        Cart cartItem = cartService.addProductToCart(productId, quantity, key) ;

        return new ResponseEntity<>(cartItem, HttpStatus.ACCEPTED);
    }
    
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProductInCart(@RequestParam String key) throws CartException, LoginException {
    	
    	List<Product> list = cartService.viewAllProducts(key) ;
    	
    	return new ResponseEntity<>(list, HttpStatus.OK);
    }
    
    
    
    @DeleteMapping("/products/remove") 
    public ResponseEntity<List<Product>> removeAProductFromCart(@RequestParam Integer productId , 
    									      		   @RequestParam String key) throws CartException, ProductException, LoginException {
    	
    	List<Product> list =cartService.removeProductFromCart(productId, key);
    	
    	return new ResponseEntity<>(list, HttpStatus.OK);
    	
    }
    
    @PutMapping("/products") 
    public ResponseEntity<List<Product>> updateProductQuantity(
												    		  @RequestParam Integer productId , 
															  @RequestParam Integer quantity,
															  @RequestParam String key) throws CartException, LoginException, ProductException {
    
    	List<Product> list = cartService.updateProductQuantity(productId, quantity, key);
    	
    	return new ResponseEntity<>(list, HttpStatus.OK);
    }
    
    
    @DeleteMapping("/products")
    public ResponseEntity<Cart> removeAllProducts(@RequestParam String key) throws CartException, LoginException {
    	Cart cart = cartService.removeAllProducts(key);
    	
    	return new ResponseEntity<>(cart, HttpStatus.OK);
    }
    



}
