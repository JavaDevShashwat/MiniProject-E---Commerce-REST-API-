package com.miniproject.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miniproject.exception.CartException;
import com.miniproject.exception.ProductException;
import com.miniproject.model.Cart;
import com.miniproject.model.Product;
import com.miniproject.model.User;
import com.miniproject.repository.CartDao;
import com.miniproject.repository.ProductDao;
import com.miniproject.repository.ProductDtoDao;
import com.miniproject.repository.UserRepository;

import lombok.Data;

@Data
@Service
public class CartServiceImpl implements CartService{
	
	@Autowired
	CartDao cartDao;

	@Autowired
	UserRepository userRepo;

	@Autowired
	ProductDao productDao;

	@Autowired
	ProductDtoDao productDtoDao;
	
	@Autowired
	ProductService productService;

	@Autowired
	CurrentUserSessionService currentUserSessionService;
	
	@Override
	public Cart addProductToCart(Integer productId, int quantity, String key) throws CartException, LoginException, ProductException {

		//cartId, Customer , List<Products> 
		
		User currentCustomer = currentUserSessionService.getCustomerDetails(key);
		
		if(currentCustomer == null) {
			throw new LoginException("Please login to add Products to the cart") ;
		}
		
		Optional<Product> optProduct = productDao.findById(productId) ;
		
		if(optProduct.isEmpty()) {
			throw new ProductException("No product available with id :"+ productId) ;
		}
		
		Product currentProduct = optProduct.get();
		
		if(currentProduct.getQuantity() < quantity) {
			throw new ProductException("Product quantity not available or Out of stock") ;
		}
		
		Cart customerCart = cartDao.findByUserId(currentCustomer.getUserId());
		
		if(customerCart == null) { // user is adding first time in the cart 
			
			customerCart = new Cart();
			
			customerCart.setUser(currentCustomer);
			
			List<Product> list = customerCart.getProducts();
			
			Product productDto = new Product( currentProduct.getProductId(),
													currentProduct.getProductName(),
													currentProduct.getPrice(), 
													quantity);
			
			currentProduct.setQuantity(currentProduct.getQuantity() - quantity);
			
			list.add(productDto);
			
			
			cartDao.save(customerCart) ;
			productDao.save(currentProduct);
			
			return customerCart;
				
		}
		else {
			
			List<Product> list = customerCart.getProducts();
			
			Product productDto = new Product( currentProduct.getProductId(),
													currentProduct.getProductName(),
													currentProduct.getPrice(), 
													quantity);
			
			currentProduct.setQuantity(currentProduct.getQuantity() - quantity);
			
			list.add(productDto);
			
			
			cartDao.save(customerCart) ;
			productDao.save(currentProduct);
			 
			return customerCart;
			 
		}
		
		
		
		
		

	}

	@Override
	public List<Product> removeProductFromCart(Integer productId, String key) throws CartException, ProductException, LoginException {
		
		User currentCustomer = currentUserSessionService.getCustomerDetails(key);
		
		if(currentCustomer == null) {
			throw new LoginException("Please login to remove Products from the cart") ;
		}
		
		Optional<Product> optProduct = productDao.findById(productId) ;
		
		if(optProduct.isEmpty()) {
			throw new ProductException("No product available with id :"+ productId) ;
		}
		
		Product currentProduct = optProduct.get();
		
		Cart customerCart = cartDao.findByUserId(currentCustomer.getUserId());
		
		if(customerCart != null) {
			List<Product> list = customerCart.getProducts();
			
			boolean flag = false;

			
			for(int i = 0; i < list.size(); i++) {
				
				Product productdto = list.get(i) ;
				
				if(productdto.getProductId() == productId) {
					
					productDtoDao.deleteById(productdto.getProductId());
					
					flag = true;
					
					currentProduct.setQuantity(currentProduct.getQuantity() + productdto.getQuantity());
					productDao.save(currentProduct);
					
					list.remove(i) ;
					break;
				}
			}
			
			if(!flag) {
				throw new ProductException("There is no product with id :"+productId);
			}
			
			customerCart.setProducts(list);
			cartDao.save(customerCart);
			
			return list;
		}
		else {
			throw new ProductException("The cart is empty") ;
		}
		
	}

	@Override
	public List<Product> updateProductQuantity(Integer productId,Integer quantity,String key) throws CartException, LoginException, ProductException {
		
		
		User currentCustomer = currentUserSessionService.getCustomerDetails(key);
		
		if(currentCustomer == null) {
			throw new LoginException("Please login to add Products to the cart") ;
		}
		
		Optional<Product> optProduct = productDao.findById(productId) ;
		
		if(optProduct.isEmpty()) {
			throw new ProductException("No product available with id :"+ productId) ;
		}
		
		Product currentProduct = optProduct.get();
		
		if(currentProduct.getQuantity() < quantity) {
			throw new ProductException("Product Out of stock") ;
		}
		
		Cart customerCart = cartDao.findByUserId(currentCustomer.getUserId());
		
		if(customerCart != null) {
			
			List<Product> list = customerCart.getProducts();
			
			boolean flag = false;
			
			for(Product productdto : list) {
				
				if(productdto.getProductId() == productId) {
					
					flag = true;
					
					currentProduct.setQuantity(currentProduct.getQuantity() - quantity);
					productdto.setQuantity(productdto.getQuantity() + quantity);
					
					productDao.save(currentProduct) ;
					productDtoDao.save(productdto) ;
					
					break;
				}
				
			}
			
			if(!flag) {
				throw new ProductException("There was no product in your cart with this id: "+" "+productId) ;
			}
			
			return list;
		}
		else {
			throw new ProductException("You have no product in the cart to update the quantity");
		}
	}

	@Override
	public Cart removeAllProducts(String key) throws CartException, LoginException {

		User currentCustomer = currentUserSessionService.getCustomerDetails(key);
		
		if(currentCustomer == null) {
			throw new LoginException("Please login to add Products to the cart") ;
		}
		
		Cart customerCart = cartDao.findByUserId(currentCustomer.getUserId());
		
		List<Product> list = customerCart.getProducts();
		System.out.println("Hi");
		if(list.size() > 0) {
			
			
			for(Product productDto : list) {
				
				Optional<Product> opt = productDao.findById(productDto.getProductId()) ;
				
				Product currentProduct = opt.get();
				
				currentProduct.setQuantity(currentProduct.getQuantity() + productDto.getQuantity());
				
				productDtoDao.delete(productDto);
				
				productDao.save(currentProduct) ;
			}
			
		}
		
		customerCart.setProducts(new ArrayList<>());
		
		return cartDao.save(customerCart) ;
	
	}

	@Override
	public List<Product> viewAllProducts(String key) throws CartException, LoginException {
		
		User currentCustomer = currentUserSessionService.getCustomerDetails(key);
		
		if(currentCustomer == null) {
			throw new LoginException("Please login to view all products in the cart") ;
		}
		
		Cart customerCart = cartDao.findByUserId(currentCustomer.getUserId());
		
		if(customerCart == null) {
			throw new CartException("You dont have anything in your cart");
		}
		
		return customerCart.getProducts();
	}
	
}
