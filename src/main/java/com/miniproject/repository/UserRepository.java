package com.miniproject.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.miniproject.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>{
	public Optional<User> findByUserMobile(String mobile);
	List<User> findByUserId(Integer userId);
}