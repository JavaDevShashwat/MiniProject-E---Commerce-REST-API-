package com.miniproject.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer userId;
	
	@NotEmpty(message = "User name is mandatory.")
	@Size(min = 3, max = 15, message = "Email id should be have minimum length of 10.")
	private String name;
	
	@Email(message = "Please enter a valid emial Id.")
	@Size(min = 10, max = 30, message = "Email id should be have minimum length of 10.")
	private String userEmail;
	
	@NotEmpty(message = "User moblie number is mandatory.")
	@Size(min = 10, max = 10, message = "Please enter valid mobile number.")
	private String userMobile;
	
	@NotEmpty(message = "User password is mandatory.")
	@Size(min = 6, max = 15, message = "Please enter password of strong length (e.g. 8-15 charecter password.)")
	private String userPassword;
	
	@NotNull(message = "User address is mandatory.")
	@OneToOne
	private Address address;
	
	public User(@NotEmpty(message = "User name is mandatory.")@Size(min = 3, max = 15, message = "Name id should be have minimum length of 10.") String name,
	@Email(message = "Please enter a valid emial Id.")@Size(min = 10, max = 30, message = "Email id should be have minimum length of 10.") String userEmail,
	@NotEmpty(message = "User moblie number is mandatory.")	@Size(min = 10, max = 10, message = "Please enter valid mobile number.") String userMobile,
	@NotEmpty(message = "User password is mandatory.")@Size(min = 6, max = 15, message = "Please enter password of strong length (e.g. 8-15 charecter password.)") String userPassword,
	Address address) {
		super();
		this.name = name;
		this.userEmail = userEmail;
		this.userMobile = userMobile;
		this.userPassword = userPassword;
		this.address = address;
	}
}
