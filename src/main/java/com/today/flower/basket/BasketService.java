package com.today.flower.basket;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import com.today.flower.orderlist.OrderListDto;
import com.today.flower.orderlist.OrderListService;
import com.today.flower.storeitem.StoreItem;
import com.today.flower.storeitem.StoreItemRepository;

import com.today.flower.basket.Basket;
import com.today.flower.basket.BasketItemDto;
import com.today.flower.basket.BasketItem;
import com.today.flower.basket.BasketItemRepository;
import com.today.flower.basket.BasketRepository;

import com.today.flower.user.SiteUser;
import com.today.flower.user.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BasketService {
	
   private final StoreItemRepository storeItemRepository; //상품엔티티
   private final UserRepository userRepository; //회원엔티티
   private final BasketRepository basketRepository;//장바구니엔티티
   private final BasketItemRepository basketItemRepository;//장바구니에 담긴 상품 엔티티
   private final OrderListService orderlistService;

   public Long addBasket(BasketItemDto basketItemDto, String email){

       StoreItem storeItem = storeItemRepository.findById(basketItemDto.getItemId())
               .orElseThrow(EntityNotFoundException::new);
      
       SiteUser siteUser = userRepository.findByEmail(email);

       Basket basket = basketRepository.findBySiteUserId(siteUser.getId());
       if(basket == null){
           basket = Basket.createBasket(siteUser);
           basketRepository.save(basket);
       }

       BasketItem savedbasketItem = basketItemRepository.findByBasketIdAndStoreItemId(basket.getId(), storeItem.getId());

       if(savedbasketItem != null){
           savedbasketItem.addCount(basketItemDto.getCount());
           return savedbasketItem.getId();
       } else {
    	   BasketItem basketItem = BasketItem.createBasketItem(basket, storeItem, basketItemDto.getCount());
           basketItemRepository.save(basketItem);
           return basketItem.getId();
       }
   }

   @Transactional(readOnly = true)
   public List<BasketDetailDto> getBasketList(String email){

       List<BasketDetailDto> basketDetailDtoList = new ArrayList<>();

       SiteUser siteUser= userRepository.findByEmail(email);
       Basket basket = basketRepository.findBySiteUserId(siteUser.getId());
       if(basket == null){
           return basketDetailDtoList;
       }

       basketDetailDtoList = basketItemRepository.findBasketDetailDtoList(basket.getId());
       return basketDetailDtoList;
   }

   @Transactional(readOnly = true)
   public boolean validateBasketItem(Long basketItemId, String email){
       SiteUser curSiteUser = userRepository.findByEmail(email);
       BasketItem basketItem = basketItemRepository.findById(basketItemId)
               .orElseThrow(EntityNotFoundException::new);
       SiteUser savedSiteUser = basketItem.getBasket().getSiteUser();

       if(!StringUtils.equals(curSiteUser.getEmail(), savedSiteUser.getEmail())){
           return false;
       }

       return true;
   }

   public void updatebasketItemCount(Long basketItemId, int count){
       BasketItem basketItem = basketItemRepository.findById(basketItemId)
               .orElseThrow(EntityNotFoundException::new);

       basketItem.updateCount(count);
   }

   public void deletebasketItem(Long basketItemId) {
       BasketItem basketItem = basketItemRepository.findById(basketItemId)
               .orElseThrow(EntityNotFoundException::new);
       basketItemRepository.delete(basketItem);
   }

   public Long orderBasketItem(List<BasketOrderDto> basketOrderDtoList, String email){
       List<OrderListDto> basketListDtoList = new ArrayList<>();

       for (BasketOrderDto basketOrderDto : basketOrderDtoList) {
           BasketItem basketItem = basketItemRepository
                           .findById(basketOrderDto.getBasketItemId())
                           .orElseThrow(EntityNotFoundException::new);

           OrderListDto orderListDto = new OrderListDto();
           orderListDto.setStoreItemId(basketItem.getStoreItem().getId());
           orderListDto.setCount(basketItem.getCount());
           basketListDtoList.add(orderListDto);
       }

       Long orderId = orderlistService.orders(basketListDtoList, email);
       for (BasketOrderDto basketOrderDto : basketOrderDtoList) {
           BasketItem basketItem = basketItemRepository
                           .findById(basketOrderDto.getBasketItemId())
                           .orElseThrow(EntityNotFoundException::new);
           basketItemRepository.delete(basketItem);
       }

       return orderId;
   }
}

