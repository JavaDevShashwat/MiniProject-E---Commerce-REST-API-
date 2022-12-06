package com.miniproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.miniproject.model.CurrentAdminSession;

@Repository
public interface CurrentAdminRepository extends JpaRepository<CurrentAdminSession, Integer>{

	CurrentAdminSession findByUuid(String uuid);
	
}
