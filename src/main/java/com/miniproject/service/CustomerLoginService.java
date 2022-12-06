package com.miniproject.service;

import javax.security.auth.login.LoginException;

import com.miniproject.model.UserDTO;

public interface CustomerLoginService {
	public String logIntoAccount(UserDTO userDTO);

	public String logOutAccount(String key);

	public boolean isLoggedIn(Integer customerId) throws LoginException;

	public boolean isLoggedInByUUID(String uuid) throws LoginException;
}