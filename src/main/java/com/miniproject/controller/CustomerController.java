package com.miniproject.controller;

import java.util.List;

import javax.security.auth.login.LoginException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.miniproject.exception.UserException;
import com.miniproject.model.User;
import com.miniproject.service.CustomerService;



@CrossOrigin(origins = "*")
@RestController
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@PostMapping("/customers")
	public ResponseEntity<User> addCustomerHandler(@Valid @RequestBody User customer) throws UserException{

		User addedCustomer = customerService.addCustomer(customer);
		
		 return new ResponseEntity<User>(addedCustomer, HttpStatus.CREATED);
		
	}
	
	@PutMapping("/customers/{key}")
	public ResponseEntity<User> updateCustomerHandler(@PathVariable("key") String key,@RequestBody User customer) throws LoginException, UserException{
		
		User updatedCustomer = customerService.updateCustomer(customer, key);
		
		return new ResponseEntity<User>(updatedCustomer, HttpStatus.CREATED);
		
	}
	
	@DeleteMapping("/customers/{key}")
	public ResponseEntity<User> removeCustomerHandler(@PathVariable("key") String key,@RequestBody User customer) throws UserException, LoginException{
		
		User deletedCustomer = customerService.removeCustomer(customer, key);
		
		return new ResponseEntity<User>(deletedCustomer, HttpStatus.OK);
		
	}
	
	@GetMapping("/customers/{customerid}")
	public ResponseEntity<User> getCustomerHandler(@PathVariable("customerid") Integer customerId) throws UserException{
		
		User existingCustomer = customerService.viewCustomer(customerId);
		
		return new ResponseEntity<User>(existingCustomer, HttpStatus.OK);
		
	}	
}
