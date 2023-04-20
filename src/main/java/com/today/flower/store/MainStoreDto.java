package com.today.flower.store;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainStoreDto {
	
	private Long storeId;
	
	private String storeName;
	
	private String storeAddr;
	
	private String openTime;
	
	private String imgUrl;
	
	@QueryProjection
	public MainStoreDto(Long storeId, String storeName, String storeAddr, String openTime, String imgUrl) {
		this.storeId = storeId;
		this.storeName = storeName;
		this.storeAddr = storeAddr;
		this.openTime = openTime;
		this.imgUrl = imgUrl;
	}
}