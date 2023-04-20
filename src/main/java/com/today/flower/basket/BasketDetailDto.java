package com.today.flower.basket;


	import lombok.Getter;
	import lombok.Setter;

	@Getter @Setter
	public class BasketDetailDto { 

	    private Long cartItemId; //장바구니 상품 아이디

	    private String itemNm; //상품명

	    private int price; //상품 금액

	    private int count; //수량

	    private String imgUrl; //상품 이미지 경로

	    public BasketDetailDto(Long basketItemId, String itemNm, int price, int count, String imgUrl){
	        this.cartItemId = basketItemId;
	        this.itemNm = itemNm;
	        this.price = price;
	        this.count = count;
	        this.imgUrl = imgUrl;
	    }
}
