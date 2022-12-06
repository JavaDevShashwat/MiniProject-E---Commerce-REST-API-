package com.miniproject.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
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
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer addressId;
	
	@Size(min = 3, max = 10, message = "Street no. should min of 3 and max of 10 Characters")
	private String houseNo;
	
	
	@NotEmpty(message = "City cannot be empty.")
	private String city;
	
	
	@NotEmpty(message = "State cannot be empty.")
	private String state;
	
	
	@NotEmpty(message = "Country cannot be empty.")
	private String country;
	
	@Size(min = 6, max = 6, message = "Pincode should be of 6 digit only")
	private Integer pincode;
	
	public Address(
			@Size(min = 3, max = 10, message = "Street no. should min of 3 and max of 10 Characters") String houseNo,
			@NotNull(message = "City cannot be null.") @NotBlank(message = "City cannot be blank.") @NotEmpty(message = "City cannot be empty.") String city,
			@NotNull(message = "State cannot be null.") @NotBlank(message = "State cannot be blank.") @NotEmpty(message = "State cannot be empty.") String state,
			@NotNull(message = "Country cannot be null.") @NotBlank(message = "Country cannot be blank.") @NotEmpty(message = "Country cannot be empty.") String country,
			@Size(min = 6, max = 6, message = "Pincode should be of 6 digit only") Integer pincode) {
		super();
		this.houseNo = houseNo;
		this.city = city;
		this.state = state;
		this.country = country;
		this.pincode = pincode;
	}
}
