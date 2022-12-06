package com.miniproject.service;

import java.time.LocalDate;
import java.util.List;

import javax.security.auth.login.LoginException;

import com.miniproject.exception.AddressException;
import com.miniproject.exception.CartException;
import com.miniproject.exception.OrderException;
import com.miniproject.model.Orders;

public interface OrderService {

	
		public Orders addOrder(Orders order, String key) throws OrderException, CartException, LoginException;
		public Orders updateOrder(Orders order, String key) throws OrderException, LoginException;
		public Orders removeOrder(Integer oriderId, String key) throws OrderException;
		public Orders viewOrder(Integer orderId) throws OrderException;
		public List<Orders> viewAllOrdersByDate(LocalDate date) throws OrderException;
		public List<Orders> viewAllOrdersByLocation(String city) throws OrderException, AddressException;
		public List<Orders> viewAllOrdersByUserId(String userid) throws OrderException;

	
}
