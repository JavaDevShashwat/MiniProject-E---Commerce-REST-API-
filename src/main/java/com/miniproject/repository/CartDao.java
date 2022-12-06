package com.miniproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.miniproject.model.Cart;
import com.miniproject.model.User;

public interface CartDao extends JpaRepository<Cart, Integer> {

	public Cart findByUserId(Integer userId);
	
}
