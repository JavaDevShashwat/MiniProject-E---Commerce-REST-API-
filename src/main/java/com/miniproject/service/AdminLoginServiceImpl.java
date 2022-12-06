package com.miniproject.service;


import java.time.LocalDateTime;
import java.util.Optional;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miniproject.model.Admin;
import com.miniproject.model.AdminLoginDTO;
import com.miniproject.model.CurrentAdminSession;
import com.miniproject.repository.AdminRepository;
import com.miniproject.repository.CurrentAdminRepository;

import net.bytebuddy.utility.RandomString;;

@Service
public class AdminLoginServiceImpl implements AdminLoginService{

	@Autowired
	private AdminRepository adminRepository;
		
	@Autowired
	private CurrentAdminRepository currentUserRepository;
	
	@Override
	public CurrentAdminSession logIntoAccount(AdminLoginDTO adminDTO) throws LoginException{
		Admin admin = adminRepository.findByEmail(adminDTO.getEmail());
		if (admin == null) {
			throw new LoginException("Please enter valid Email Id!");
		}
		Optional<CurrentAdminSession> oC = currentUserRepository.findById(admin.getAdminId());
		if (oC.isPresent()) {
			throw new LoginException( "User already logged in with this number.");
		} 
		if (admin.getPassword().equals(adminDTO.getPassword())) {
			String key = RandomString.make(4);
			return currentUserRepository.save(new CurrentAdminSession(admin.getAdminId(), "Admin", LocalDateTime.now(), key));
		} else {
			throw new LoginException( "Please Enter valid password.");
		}
	}

	@Override
	public CurrentAdminSession logOutAccount(String uuid) throws LoginException{
		CurrentAdminSession currentAdmin = currentUserRepository.findByUuid(uuid);
		if(currentAdmin == null)
			throw new LoginException( "User does not exist by id" + uuid);
		else {
			currentUserRepository.delete(currentAdmin);
			return currentAdmin;
		}
			
	}

	
}
