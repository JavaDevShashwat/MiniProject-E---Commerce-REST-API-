package com.miniproject.service;

import java.util.List;
import java.util.Optional;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miniproject.exception.AddressException;
import com.miniproject.model.Address;
import com.miniproject.model.CurrentUserSession;
import com.miniproject.model.User;
import com.miniproject.repository.AddressDao;
import com.miniproject.repository.UserRepository;

@Service
public class AddressServiceImpl implements AddressService{
	@Autowired
	private AddressDao adao;
	
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private CurrentUserSessionService currentUserSessionService;
	
	@Override
	public Address addAddress(Address add, String key) throws AddressException, LoginException {
	        Address	address= adao.save(add);
	        
	        User currentUser = currentUserSessionService.getCustomerDetails(key);
	        
	        currentUser.setAddress(address);
	        
	        userRepo.save(currentUser);
	        
	        return address;
	}

	@Override
	public Address updateAddress(Address add) throws AddressException {
	       Optional<Address> opt =	adao.findById(add.getAddressId());
	       if(opt.isPresent()) {
	    	   return adao.save(add);
	       }
	       else {
			throw new AddressException("Address not found");
		}
	}

	@Override
	public Address removeAddress(Integer addrId) throws AddressException {
	      
		Address existingAdd  = 	adao.findById(addrId).orElseThrow(()->new AddressException("Address does not exist :"+addrId));
	      return existingAdd;
	      
	      
	}

	@Override
	public List<Address> viewAllAddress() throws AddressException {
	 List<Address> opt= adao.findAll();
		if(opt.size()>0) {
			return opt;
		}
		else {
			throw new AddressException("Address does not exist");
		}
	}

	@Override
	public Address viewAddress(Integer id) throws AddressException {
		Optional<Address> opt= adao.findById(id);
		if(opt.isPresent()) {
			return opt.get();
		}
		else {
			throw new AddressException("Address does not exist");
		}
	}

}
