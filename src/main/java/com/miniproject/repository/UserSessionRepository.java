package com.miniproject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miniproject.model.CurrentUserSession;

public interface UserSessionRepository extends JpaRepository<CurrentUserSession, Integer> {

	public Optional<CurrentUserSession> findByUserId(Integer userId);

	public Optional<CurrentUserSession> findByUuid(String uuid);

}
