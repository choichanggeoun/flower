package com.today.flower.orderlist;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.today.flower.orderlist.OrderListHistDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
@Controller
@RequiredArgsConstructor
public class OrderListController {
	
   private final OrderListService orderListService;
   
   @PostMapping(value="/order")
// 1.
   public @ResponseBody ResponseEntity order(@RequestBody @Valid OrderListDto orderDto, BindingResult bindingResult, Principal principal) {
	   if(bindingResult.hasErrors()) { //--2
		   StringBuilder sb = new StringBuilder();
		   List<FieldError> fieldErros = bindingResult.getFieldErrors();
		   for( FieldError fieldError : fieldErros) {
			   sb.append(fieldError.getDefaultMessage() );
		   }
           return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);		   // 3--
	   }
	   String email = principal.getName(); // --4
	   Long orderListId;
	   try {
	   orderListId = orderListService.order(orderDto, email); // --5
	   }catch(Exception e) {
		   return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
	   }
	   return new ResponseEntity<Long>(orderListId, HttpStatus.OK); // --6
	   
   }
   @GetMapping(value = {"/orders", "/orders/{page}"})
   public String orderHist(@PathVariable("page") Optional<Integer> page, Principal principal, Model model){

       Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 4);
       Page<OrderListHistDto> ordersListHistDtoList = orderListService.getOrderList(principal.getName(), pageable);

       model.addAttribute("orders", ordersListHistDtoList);
       model.addAttribute("page", pageable.getPageNumber());
       model.addAttribute("maxPage", 5);

       return "order/orderHist";
   }

   @PostMapping("/orderlist/{orderListId}/cancel")
   public @ResponseBody ResponseEntity cancelOrder(@PathVariable("orderListId") Long orderListId , Principal principal){

       if(!orderListService.validateOrderList(orderListId, principal.getName())){
           return new ResponseEntity<String>("주문 취소 권한이 없습니다.", HttpStatus.FORBIDDEN);
       }

       orderListService.cancelOrderList(orderListId);
       return new ResponseEntity<Long>(orderListId, HttpStatus.OK);
   }

}


