package com.miniproject.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer adminId;
	private String adminName;
	private String email;
	private String password;
	private String mobile;
	
	public Admin(String adminName, String email, String password, String mobile) {
		super();
		this.adminName = adminName;
		this.email = email;
		this.password = password;
		this.mobile = mobile;
	}
}
