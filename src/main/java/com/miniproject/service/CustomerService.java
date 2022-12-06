package com.miniproject.service;

import java.util.List;

import javax.security.auth.login.LoginException;

import com.miniproject.exception.UserException;
import com.miniproject.model.User;


public interface CustomerService {

	public User addCustomer(User cust) throws UserException ;
	
	public User updateCustomer(User cust, String key) throws UserException, LoginException  ;
	
	public User removeCustomer(User cust, String key) throws UserException, LoginException;
	
	public User viewCustomer(Integer customerId)  throws UserException;
	
}
