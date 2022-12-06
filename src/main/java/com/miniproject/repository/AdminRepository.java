package com.miniproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.miniproject.model.Admin;
import com.miniproject.model.AdminDTO;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer>{

	Admin findByEmail(String email);
	Admin save(AdminDTO admin);
}
