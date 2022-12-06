package com.miniproject.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miniproject.exception.AddressException;
import com.miniproject.exception.CartException;
import com.miniproject.exception.OrderException;
import com.miniproject.model.Address;
import com.miniproject.model.CurrentUserSession;
import com.miniproject.model.Orders;
import com.miniproject.model.Product;
import com.miniproject.model.User;
import com.miniproject.repository.CartDao;
import com.miniproject.repository.OrderDao;
import com.miniproject.repository.UserRepository;
import com.miniproject.repository.UserSessionRepository;



@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private OrderDao oDao;
	
	@Autowired
	UserSessionRepository uSessionDao;
	
	@Autowired
	UserRepository cDao;
	
	@Autowired
	CartDao cartRepo;

	@Override
	public Orders addOrder(Orders order, String key) throws OrderException, CartException, LoginException {
		
		 Optional<CurrentUserSession> user = uSessionDao.findByUuid(key);
		 
		 if( user.isPresent() ) {
			 
			 Integer customerId = user.get().getUserId();
			 
			 Optional<User> ourCustomer = cDao.findById(customerId);
			 
			 Address addr = ourCustomer.get().getAddress();
			 
			 
			 Orders currOrder = new Orders();
			 
			 currOrder.setOrderDate(LocalDate.now());
			 currOrder.setOrderAddress(new Address(addr.getHouseNo(), addr.getCity(), addr.getState(), addr.getCountry(), addr.getPincode()));
			 
			 currOrder.setUser(ourCustomer.get());
			 currOrder.setOrderStatus("Order confirmed");
			 List<Product> list = cartRepo.findByUserId(customerId).getProducts();
			 if( list.size() < 1) {
				 throw new CartException("Add product to the cart first...");
			 }
			 currOrder.setPList(list);
			 
			 return oDao.save(currOrder);
			 
		 }
		 else {
			 throw new LoginException("Login first");
		 }
		 
		 
	}

	@Override
	public Orders updateOrder(Orders order, String key) throws OrderException, LoginException {
		
		if( uSessionDao.findByUuid(key) != null ) {
		
			Optional<Orders> opt=  oDao.findById(order.getOrderId());
			
			if(opt.isPresent()) {
				return oDao.save(order);
			}
			else {
				throw new OrderException("Order does not exist");
			}
		}
		else {
			throw new LoginException("Please, Login First...");
		}
		
	}

	@Override
	public Orders removeOrder(Integer orderId, String key) throws OrderException {
		
		Orders	existingProduct = oDao.findById(orderId).orElseThrow(()->new OrderException("Order does not exist with id :"));
		
		oDao.delete(existingProduct);
		
		return existingProduct;
	}

	@Override
	public Orders viewOrder(Integer orderId) throws OrderException {
		Optional<Orders> opt1=  oDao.findById(orderId);
		
		if(opt1.isPresent()) {
			return opt1.get();
		}
		else {
			throw new OrderException("No order found");
		}
	}

	@Override
	public List<Orders> viewAllOrdersByDate(LocalDate date) throws OrderException {
		List<Orders> orders= oDao.findByOrderDate(date);
		
		if(orders.size()>0) {
			
			return orders;
		}
		else {
			throw new OrderException("Order doesn't exist on this date.");
		}
		
	}

	@Override
	public List<Orders> viewAllOrdersByLocation(String loc) throws OrderException, AddressException {
		
		List<Orders> list= oDao.getOrderByCity(loc);
		
		if( list.size() < 1) {
			throw new OrderException("No order found with this userId.");
		}
		return list;
	}

	@Override
	public List<Orders> viewAllOrdersByUserId(String userid) throws OrderException {

		List<Orders> list = oDao.getOrdersByUserId(userid);
		
		if( list.size() < 1) {
			throw new OrderException("No order found with this userId.");
		}
		
		return list;
	}

}
