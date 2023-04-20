package com.today.flower.faq;

import java.security.Principal;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.today.flower.user.SiteUser;
import com.today.flower.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller

public class AnswerController {

		private final QuestionService questionService;
		
		private final AnswerService answerService;
		
		private final UserService userService;
		
		@PreAuthorize("isAuthenticated()")
		@PostMapping("/create/{id}")
		public String createAnswer(Model model, @PathVariable("id") Integer 
				id, @Valid AnswerForm answerForm,BindingResult bindingResult,Principal
				principal) {	
			Question question = this.questionService.getQuestion(id); // 답변을 저장한다.
			SiteUser siteUser = this.userService.getUser(principal.getName());
			if (bindingResult.hasErrors()) {
	            model.addAttribute("question", question);
	            return "faqQuestion_detel";
	        }
			
			this.answerService.create(question, answerForm.getContent(),siteUser );
			return  String.format("redirect:/faqQuestion/detel/%s",id);
		}
}
