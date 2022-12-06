package com.miniproject.service;

import java.util.List;
import java.util.Optional;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miniproject.exception.UserException;
import com.miniproject.model.CurrentUserSession;
import com.miniproject.model.User;
import com.miniproject.repository.AddressDao;
import com.miniproject.repository.UserRepository;
import com.miniproject.repository.UserSessionRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	UserRepository customerDao;
	
	@Autowired
	CurrentUserSessionService currentUserSessionService;
	
	@Autowired
	UserSessionRepository currentUserSessionDao;
	
	@Autowired
	AddressDao addressDao ;
	
	@Override
	public User addCustomer(User cust) throws UserException {
		
		Optional<User> opt = customerDao.findByUserMobile(cust.getUserMobile()) ;
		
		if(opt.isPresent()) {
			throw new UserException("Customer already Exist With this Mobile Number");
		}
		
		return customerDao.save(cust);
	}

	@Override
	public User updateCustomer(User cust, String key) throws UserException, LoginException {
		
		User customerDetails = currentUserSessionService.getCustomerDetails(key) ;
		
		if(customerDetails == null) {
			throw new LoginException("No user Found | Login first");
		}else if( cust.getUserMobile().toCharArray().length != 10 ){
			
			throw new UserException("Mobile Number can only be of 10 digit");
		}
		
		if(cust.getUserId() == customerDetails.getUserId()) {
			return customerDao.save(cust) ;
		}
		else {
			throw new UserException("Can't change UserID!") ;
		}
		
	}

	@Override
	public User removeCustomer(User cust, String key) throws UserException, LoginException {
		
//		Optional<Customer> opt = customerDao.findById(cust.getCustomerId());
//		
//		if(opt.isEmpty()) {
//			throw new CustomerException("Customer is not registered");
//		}
		
		User currentCustomer = currentUserSessionService.getCustomerDetails(key);
		
		if(currentCustomer != null) {
			
			if(cust.getUserId() == currentCustomer.getUserId()) {
				
				customerDao.delete(cust);
				
				Optional<CurrentUserSession> opt = currentUserSessionDao.findByUuid(key) ;
				
				CurrentUserSession currentSession = opt.get();
				
				currentUserSessionDao.delete(currentSession);
				return cust;
				
				
			}
			else {
				throw new UserException("Invalid Customer ID") ;
			}
			
		}
		else {
			throw new UserException("Invalid UUID key");
		}
		
		
		
		
	}

	@Override
	public User viewCustomer(Integer customerId) throws UserException {
		
		Optional<User> cust = customerDao.findById(customerId);
		
		cust.orElseThrow(()-> new UserException("Customer doesn't found..."));
		
		return cust.get();
		
	}

}

















