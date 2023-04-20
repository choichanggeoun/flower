package com.today.flower.user;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.today.flower.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public SiteUser create(String userId,String password, String userName, String email, String phoneNum, String address) {
		SiteUser user = new SiteUser();
		user.setUserId(userId);
		user.setPassword(passwordEncoder.encode(password));
		user.setUserName(userName);
		user.setEmail(email);
		user.setPhoneNum(phoneNum);
		user.setAddress(address);
		this.userRepository.save(user);
		return user;
	}
		public SiteUser getUser(String userId) {
			Optional<SiteUser> siteUser = this.userRepository.findByUserId(userId);
			if (siteUser.isPresent()) {
				return siteUser.get();
			}else {
				throw new DataNotFoundException("siteuser not found");
			}
		}
		
		public SiteUser saveSiteUser(SiteUser siteUser){
	        validateDuplicateSiteUser(siteUser);
	        return userRepository.save(siteUser);
	    }
		
		private void validateDuplicateSiteUser(SiteUser siteUser){
	        SiteUser findsiteUser = userRepository.findByEmail(siteUser.getEmail());
	        if(findsiteUser != null){
	            throw new IllegalStateException("이미 가입된 회원입니다.");
	        }
	    }
	
}
