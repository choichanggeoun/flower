package com.today.flower.faq;

import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.today.flower.user.SiteUser;
import com.today.flower.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/faqQuestion")
@RequiredArgsConstructor
@Controller
public class QuestionController {

	private final QuestionService questionService;
	private final UserService userService;
	
	@GetMapping("/list")
	public String list(Model model,@RequestParam(value="page",defaultValue="0")int page) {
		Page<Question> paging = this.questionService.getList(page);
		model.addAttribute("paging",paging);
		return "faqQuestion_list";
	}
	
	@GetMapping(value = "/detel/{id}")
	public String detel(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
		Question question = this.questionService.getQuestion(id);
		model.addAttribute("question", question);
		return "faqQuestion_detel";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/create")
	public String questionCreate(QuestionForm questionForm) {
		return "faqQuestion_form";
	}
	
	@PreAuthorize("isAuthenticated()")

    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult 
    		bindingResult,Principal principal) {
    	if (bindingResult.hasErrors()) {
            return "faqQuestion_form";
    	}
    	SiteUser siteUser = this.userService.getUser(principal.getName());
        this.questionService.create(questionForm.getSubject(),
        		questionForm.getContent(),siteUser);
        return "redirect:/faqQuestion/list";
	}

}


