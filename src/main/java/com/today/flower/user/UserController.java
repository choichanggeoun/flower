package com.today.flower.user;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/user")
@RequiredArgsConstructor
@Controller
public class UserController {
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;

    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup_form";
        }

        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect", 
                    "2개의 패스워드가 일치하지 않습니다.");
            return "signup_form";
        }

        try {
        userService.create(userCreateForm.getUserId(),userCreateForm.getPassword1(), userCreateForm.getUserName(), 
                userCreateForm.getEmail(), userCreateForm.getPhoneNum(), userCreateForm.getAddress());
        }catch(DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "signup_form";
        }catch(Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup_form";
        }

        return "redirect:/";
    }
    
	/*
	@GetMapping(value = "/signup")
    public String UserForm(Model model){
        model.addAttribute("userCreateForm", new UserCreateForm());
        return "memberForm";
    }

    @PostMapping(value = "/signup")
    public String newMember(@Valid UserCreateForm userCreateForm, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            return "memberForm";
        }

        try {
            SiteUser siteUser = SiteUser.createSiteUser(userCreateForm, passwordEncoder);
            userService.saveSiteUser(siteUser);
        } catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "memberForm";
        }

        return "redirect:/";
    }
	*/
	
    @GetMapping("/login")
    public String login() {
        return "login_form";
    }

}