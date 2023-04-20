package com.today.flower.basket;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;

import com.today.flower.basket.BasketItemDto;
import com.today.flower.basket.BasketService;
import com.today.flower.basket.BasketOrderDto;
import com.today.flower.basket.BasketDetailDto;
	
@Controller
	@RequiredArgsConstructor
	public class BasketController {
		
		private final BasketService basketService;
		
		@PostMapping(value="/basket")
		 public @ResponseBody ResponseEntity orderList( @RequestBody @Valid BasketItemDto basketItemDto, 
				   BindingResult bindingResult, Principal principal	){
			//장바구니에 담을 상품 정보를 받는 cartItemDto객체에 데이터 바인딩시 에러가 있는 검사합니다
			if( bindingResult.hasErrors()) {
				StringBuilder sb = new StringBuilder();
				List<FieldError> fieldErrors = bindingResult.getFieldErrors();
				for(FieldError fieldError  : fieldErrors) {
					sb.append(fieldError.getDefaultMessage());
				}
				return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST ); 
			}
			// 현재 로그한 회원의 이메일 정보를 변수에 저장합니다
			String email = principal.getName();
			Long basketItemId;
			// 화면으로부터 넘어온 장바구니에 담을 상품정보와 현재 로그인한 회원의 이메일 정보를 이용하여 
			// 장바구니에 상품을 담을 로직을 호출합니다.
			try {
				basketItemId = basketService.addBasket(basketItemDto, email);
			}catch(Exception e) {
				return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST ); 
			}
			 // 결과값으로 생성된 장바구니 상품 아이디와 요청이 성공하였다는 HTTP 응답 상태 코드를 반환합니다.
			return new ResponseEntity<Long>(basketItemId, HttpStatus.OK);
		}
	
		@GetMapping(value = "/basket")
	    public String orderListHist(Principal principal, Model model){
	        List<BasketDetailDto> basketDetailList = basketService.getBasketList(principal.getName());
	        model.addAttribute("basketItems", basketDetailList);
	        return "basket/basketList";
	    }

	    @PatchMapping(value = "/basketItem/{baskeItemId}")
	    public @ResponseBody ResponseEntity updateCartItem(@PathVariable("basketItemId") Long basketItemId, int count, Principal principal){

	        if(count <= 0){
	            return new ResponseEntity<String>("최소 1개 이상 담아주세요", HttpStatus.BAD_REQUEST);
	        } else if(!basketService.validateBasketItem(basketItemId, principal.getName())){
	            return new ResponseEntity<String>("수정 권한이 없습니다.", HttpStatus.FORBIDDEN);
	        }

	        basketService.updatebasketItemCount(basketItemId, count);
	        return new ResponseEntity<Long>(basketItemId, HttpStatus.OK);
	    }

	    @DeleteMapping(value = "/basketItem/{basketItemId}")
	    public @ResponseBody ResponseEntity deleteBasketItem(@PathVariable("basketItemId") Long basketItemId, Principal principal){

	        if(!basketService.validateBasketItem(basketItemId, principal.getName())){
	            return new ResponseEntity<String>("수정 권한이 없습니다.", HttpStatus.FORBIDDEN);
	        }

	        basketService.deletebasketItem(basketItemId);

	        return new ResponseEntity<Long>(basketItemId, HttpStatus.OK);
	    }

	    @PostMapping(value = "/basket/orders")
	    public @ResponseBody ResponseEntity orderbasketItem(@RequestBody BasketOrderDto basketOrderDto, Principal principal){

	        List<BasketOrderDto> basketOrderDtoList = basketOrderDto.getBasketOrderDtoList();

	        if(basketOrderDtoList == null || basketOrderDtoList.size() == 0){
	            return new ResponseEntity<String>("주문할 상품을 선택해주세요", HttpStatus.FORBIDDEN);
	        }

	        for (BasketOrderDto basketOrder : basketOrderDtoList) {
	            if(!basketService.validateBasketItem(basketOrder.getBasketItemId(), principal.getName())){
	                return new ResponseEntity<String>("주문 권한이 없습니다.", HttpStatus.FORBIDDEN);
	            }
	        }

	        Long orderId = basketService.orderBasketItem(basketOrderDtoList, principal.getName());
	        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
	    }

	}
	
	
	

