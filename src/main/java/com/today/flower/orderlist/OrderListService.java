package com.today.flower.orderlist;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import com.today.flower.storeitem.StoreItemRepository;
import com.today.flower.user.SiteUser;
import com.today.flower.user.UserRepository;
import com.today.flower.storeitem.StoreItem;
import com.today.flower.storeitem.StoreItemImg;
import com.today.flower.storeitem.StoreItemImgRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderListService {

     private final StoreItemRepository storeItemRepository;
     private final UserRepository userRepository;
     private final OrderListRepository orderListRepository;
     private final StoreItemImgRepository storeItemImgRepository;

     public Long order(OrderListDto orderListDto, String userName) {
        
        StoreItem storeItem = storeItemRepository.findById(orderListDto.getStoreItemId())
                             .orElseThrow(EntityNotFoundException::new );
        
        SiteUser siteUser = userRepository.findByUserName(userName);
        
        List<OrderListItem> orderItemList = new ArrayList<>();
        OrderListItem orderItem = 
              OrderListItem.createOrderListItem(storeItem, orderListDto.getCount() );
        orderItemList.add(orderItem);
        OrderList orderList = OrderList.createOrderList(siteUser, orderItemList);
        orderListRepository.save(orderList);
        return orderList.getId();
     }
     
     @Transactional(readOnly = true)
     public Page<OrderListHistDto> getOrderList(String email, Pageable pageable) {

         List<OrderList> orders = orderListRepository.findOrderList(email, pageable);
         Long totalCount = orderListRepository.countOrder(email);

         List<OrderListHistDto> orderListHistDtos = new ArrayList<>();

         for (OrderList orderlist : orders) {
             OrderListHistDto orderlistHistDto = new OrderListHistDto(orderlist);
             List<OrderListItem> orderlistItems = orderlist.getOrderListItems();
             for (OrderListItem orderlistItem : orderlistItems) {
                 StoreItemImg storeItemImg = storeItemImgRepository.findByStoreItemIdAndRepimgYn
                         (orderlistItem.getStoreItem().getId(), "Y");
                 OrderListItemDto orderlistItemDto =
                         new OrderListItemDto(orderlistItem, storeItemImg.getImgUrl());
                 orderlistHistDto.addOrderListItemDto(orderlistItemDto);
             }

             orderListHistDtos.add(orderlistHistDto);
         }

         return new PageImpl<OrderListHistDto>(orderListHistDtos, pageable, totalCount);
     }

     @Transactional(readOnly = true)
     public boolean validateOrderList(Long orderListId, String email){
         SiteUser curSiteUser = userRepository.findByEmail(email);
         OrderList orderList = orderListRepository.findById(orderListId)
                 .orElseThrow(EntityNotFoundException::new);
        SiteUser savedSiteUser = orderList.getSiteUser();

         if(!StringUtils.equals(curSiteUser.getEmail(), savedSiteUser.getEmail())){
             return false;
         }

         return true;
     }

     public void cancelOrderList(Long orderId){
         OrderList orderlist = orderListRepository.findById(orderId)
                 .orElseThrow(EntityNotFoundException::new);
         orderlist.cancelOrder();
     }

     public Long orders(List<OrderListDto> orderListDtoList, String email){

         SiteUser siteUser = userRepository.findByEmail(email);
         List<OrderListItem> orderListItemList = new ArrayList<>();

         for (OrderListDto orderListDto : orderListDtoList) {
             StoreItem storeItem = storeItemRepository.findById(orderListDto.getStoreItemId())
                     .orElseThrow(EntityNotFoundException::new);

             OrderListItem orderListItem = OrderListItem.createOrderListItem(storeItem, orderListDto.getCount());
             orderListItemList.add(orderListItem);
         }

         OrderList orderList = OrderList.createOrderList(siteUser, orderListItemList);
         orderListRepository.save(orderList);

         return orderList.getId();
     }

 }