package com.today.flower.storeitem;

import com.today.flower.ItemSellStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreItemSearchDto {

	private ItemSellStatus searchSellStatus;
	
	private String searchBy;
	
	private String searchQuery = "";
}