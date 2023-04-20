package com.today.flower.orderlist;

import com.today.flower.orderlist.OrderListHistDto;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

public class OrderListHistDto {

    public OrderListHistDto(OrderList orderList){
        this.orderId = orderList.getId();
        this.orderDate = orderList.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.orderListStatus = orderList.getOrderListStatus();
    }

    private Long orderId; //주문아이디
    private String orderDate; //주문날짜
    private OrderListStatus orderListStatus; //주문 상태

    private List<OrderListItemDto> orderlistItemDtoList = new ArrayList<>();

    //주문 상품리스트
    public void addOrderListItemDto(OrderListItemDto orderlistItemDto){
        orderlistItemDtoList.add(orderlistItemDto);
    }
}