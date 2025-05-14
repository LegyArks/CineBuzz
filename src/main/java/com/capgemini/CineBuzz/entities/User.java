package com.capgemini.CineBuzz.entities;

import java.util.List;

import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
;

@Entity
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    
   
    @NotBlank(message = "Full name is required")
    @Size(min = 5, message = "Full name must be at least 5 characters")
    private String name;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
    
    @NotBlank(message = "Password is required")
    @Pattern(regexp = "^[a-zA-Z0-9]{6,}$", 
             message = "Password must be at least 6 alphanumeric characters")
    private String password;
    

    private String userType;
    
    @NotNull(message = "Phone number is required")
    @Pattern(regexp = "^[789]\\d{9}$", 
             message = "Phone number must be 10 digits starting with 7, 8, or 9")
    private String phoneNumber;
    
    @OneToMany(mappedBy = "user")
    private List<Booking> booking;

    
    public User() {
    }

    public User(Long userId, String name, String email, String password, String phoneNumber,String userType) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.userType = userType;
    }

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", name=" + name + ", email=" + email + ", password=" + password
				+ ", userType=" + userType + ", phoneNumber=" + phoneNumber + "]";
	}

    
    

}