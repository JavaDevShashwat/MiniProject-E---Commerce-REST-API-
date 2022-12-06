package com.miniproject.service;

import javax.security.auth.login.LoginException;
import com.miniproject.model.AdminLoginDTO;
import com.miniproject.model.CurrentAdminSession;

public interface AdminLoginService {

	public CurrentAdminSession logIntoAccount(AdminLoginDTO adminDTO)throws LoginException;

	public CurrentAdminSession logOutAccount(String uuid)throws LoginException;
}
