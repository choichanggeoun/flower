package com.today.flower.storeitem;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.today.flower.ItemSellStatus;
import com.today.flower.store.Store;

import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreItemFormDto {
	
	  private Long id;

	    @NotBlank(message = "상품명은 필수 입력 값입니다.")
	    private String itemNm;

	    @NotNull(message = "가격은 필수 입력 값입니다.")
	    private Integer price;

	    @NotBlank(message = "상품 상세는 필수 입력 값입니다.")
	    private String itemDetail;

	    @NotNull(message = "재고는 필수 입력 값입니다.")
	    private Integer stockNumber;
	    
	    @NotNull(message = "업체명은 필수 입력 값입니다.")
	    @JoinColumn(name = "store_id")
	    private Store store;

	    private ItemSellStatus itemSellStatus;

	    private List<StoreItemImgDto> storeItemImgDtoList = new ArrayList<>();

	    private List<Long> storeItemImgIds = new ArrayList<>();

	    private static ModelMapper modelMapper = new ModelMapper();

	    public StoreItem createItem(){
	        return modelMapper.map(this, StoreItem.class);
	    }

	    public static StoreItemFormDto of(StoreItem storeItem){
	        return modelMapper.map(storeItem,StoreItemFormDto.class);
	    }
}
