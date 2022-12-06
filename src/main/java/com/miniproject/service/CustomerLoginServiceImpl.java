package com.miniproject.service;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miniproject.model.CurrentUserSession;
import com.miniproject.model.User;
import com.miniproject.model.UserDTO;
import com.miniproject.repository.UserRepository;
import com.miniproject.repository.UserSessionRepository;

@Service
public class CustomerLoginServiceImpl implements CustomerLoginService {

	@Autowired
	private UserRepository uRepo;
	@Autowired
	private UserSessionRepository uSessionRepo;

	@Override
	public String logIntoAccount(UserDTO customerDTO) {
		Optional<User> opt = uRepo.findByUserMobile(customerDTO.getMobile());

		if (!opt.isPresent()) {
			return "Please enter valid Mobile number!";
		}

		User customer1 = opt.get();
		Integer userId = customer1.getUserId();
		Optional<CurrentUserSession> currUseropt1 = uSessionRepo.findById(userId);

		if (currUseropt1.isPresent()) {
			return "User already logged in with this number.";
		}

		if (customer1.getUserPassword().equals(customerDTO.getPassword())) {

			String key = RandomString.getRandomNumberString();
			CurrentUserSession currentUserSession = new CurrentUserSession(userId, key, LocalDateTime.now());

			uSessionRepo.save(currentUserSession);

			return currentUserSession.toString();
		} else {
			return "Please Enter valid password.";
		}

	}

	@Override
	public String logOutAccount(String key) {
		Optional<CurrentUserSession> currUserOpt = uSessionRepo.findByUuid(key);

		if (currUserOpt.isPresent()) {
			CurrentUserSession currUser1 = currUserOpt.get();

			uSessionRepo.delete(currUser1);
			return "User logged out successfully.";
		}
		return "User does not exist, Enter correct uuid";
	}

	@Override
	public boolean isLoggedIn(Integer customerId) throws LoginException {
		Optional<CurrentUserSession> opt = uSessionRepo.findByUserId(customerId);
		if (opt.isPresent())
			return true;
		else
			throw new LoginException("LogIn first !! ");
	}

	@Override
	public boolean isLoggedInByUUID(String uuid) throws LoginException {
		Optional<CurrentUserSession> opt = uSessionRepo.findByUuid(uuid);
		if (opt.isPresent())
			return true;
		else
			throw new LoginException("LogIn first !! ");
	}

}