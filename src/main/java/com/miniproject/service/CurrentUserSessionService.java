package com.miniproject.service;

import javax.security.auth.login.LoginException;
import com.miniproject.model.CurrentUserSession;
import com.miniproject.model.User;

public interface CurrentUserSessionService {

	public CurrentUserSession getCurrentUserSession(String key) throws LoginException;
	
	public Integer getCurrentUserCustomerId(String key) throws LoginException;
	
	public User getCustomerDetails(String key) throws LoginException;
	
}
