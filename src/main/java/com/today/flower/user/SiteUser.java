package com.today.flower.user;

import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(name="siteuser")
@ToString
public class SiteUser {

	@Id
	@Column(name = "siteuser_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String userId;
	
	private String password;
	
	private String userName;
	
	@Column(unique = true)
	private String email;
	
	@Column(unique = true)
	private String phoneNum;
	
	private String address;
	
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    public static SiteUser createSiteUser(UserCreateForm userCreateForm, PasswordEncoder passwordEncoder){
        SiteUser siteUser = new SiteUser();
        siteUser.setUserId(userCreateForm.getUserId());
        String password = passwordEncoder.encode(userCreateForm.getPassword1());
        siteUser.setPassword(password);
        siteUser.setUserName(userCreateForm.getUserName());
        siteUser.setEmail(userCreateForm.getEmail());
        siteUser.setPhoneNum(userCreateForm.getPhoneNum());
        siteUser.setAddress(userCreateForm.getAddress());
        siteUser.setUserRole(userCreateForm.getUserRole());
        return siteUser;
    }
}