package com.miniproject.service;

import java.util.Optional;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.miniproject.model.CurrentUserSession;
import com.miniproject.model.User;
import com.miniproject.repository.UserRepository;
import com.miniproject.repository.UserSessionRepository;

@Service
public class CurrentUserrSessionServiceImpl implements CurrentUserSessionService {

	@Autowired
	private UserSessionRepository currentUserSessionDao;
	
	@Autowired
	private UserRepository userRepo;
	
	@Override
	public CurrentUserSession getCurrentUserSession(String key) throws LoginException {
		
		Optional<CurrentUserSession> currentUser = currentUserSessionDao.findByUuid(key);
		
		if(!currentUser.isPresent()) {
			throw new  LoginException("User has not logged in");
		}
		
		return currentUser.get();
	}

	@Override
	public Integer getCurrentUserCustomerId(String key) throws LoginException {
		Optional<CurrentUserSession> currentUser = currentUserSessionDao.findByUuid(key);
		
		if(!currentUser.isPresent()) {
			throw new  LoginException("User has not logged in");
		}
		
		return currentUser.get().getUserId();
	}

	@Override
	public User getCustomerDetails(String key) throws LoginException {
		Optional<CurrentUserSession> currentUser = currentUserSessionDao.findByUuid(key);
		
		if(!currentUser.isPresent()) {
			throw new  LoginException("User has not logged in");
		}
		
		Integer customerId = currentUser.get().getUserId();
		
		return userRepo.findById(customerId).get();
	}

}
