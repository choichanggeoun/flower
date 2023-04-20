package com.today.flower;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.today.flower.store.MainStoreDto;
import com.today.flower.store.StoreSearchDto;
import com.today.flower.store.StoreService;
import com.today.flower.storeitem.StoreItemService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {
	
	private final StoreService storeService;
	
	@GetMapping(value= "/")
	public String main(StoreSearchDto storeSearchDto, Optional<Integer> page, Model model) {
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
		Page<MainStoreDto> stores = storeService.getMainStorePage(storeSearchDto, pageable);
		model.addAttribute("stores", stores);
		model.addAttribute("storeSearchDto", storeSearchDto);
		model.addAttribute("maxPage", 5);
		return "store/main";
	}
}
