package com.miniproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miniproject.model.Cart;


public interface CartRepository extends JpaRepository<Cart, Integer>{

    //List<Cart> findAllByUserOrderByCreatedDataDesc(User user);
}
