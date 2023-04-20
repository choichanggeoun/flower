package com.today.flower.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
   
public interface UserRepository extends JpaRepository<SiteUser,Long>  {
	
	Optional<SiteUser> findByUserId(String userId);
	
	//Optional<SiteUser> findByUserName(String userName);
	
	
	SiteUser findByEmail(String email);
	
	SiteUser findByUserName(String userName);
	
}
