package com.today.flower.orderlist;

public class OrderListItemDto {

	public OrderListItemDto(OrderListItem orderItem, String imgUrl){
        this.itemNm = orderItem.getStoreItem().getItemNm();
        this.count = orderItem.getCount();
        this.orderPrice = orderItem.getOrderPrice();
        this.imgUrl = imgUrl;
    }

    private String itemNm; //상품명
    private int count; //주문 수량

    private int orderPrice; //주문 금액
    private String imgUrl; //상품 이미지 경로


}
