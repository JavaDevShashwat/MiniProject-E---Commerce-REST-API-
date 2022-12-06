package com.miniproject.controller;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miniproject.model.AdminLoginDTO;
import com.miniproject.model.CurrentAdminSession;
import com.miniproject.service.AdminLoginService;

@RestController
@RequestMapping("/login")
public class AdminLoginController {
	@Autowired
	private AdminLoginService adminLoginService;

	@PostMapping("/adminLogin")
	public ResponseEntity<CurrentAdminSession> loginCustomerr(@RequestBody AdminLoginDTO adminDTO) throws LoginException  {
		CurrentAdminSession user = adminLoginService.logIntoAccount(adminDTO);
		return new ResponseEntity<CurrentAdminSession>(user,HttpStatus.OK);
	}

	@DeleteMapping("/adminLogout/{uuid}")
	public ResponseEntity<CurrentAdminSession> logOutCustomerr(@PathVariable("uuid") String key) throws LoginException {
		CurrentAdminSession user = adminLoginService.logOutAccount(key);
		return new ResponseEntity<CurrentAdminSession>(user, HttpStatus.OK);
		
	}
}
