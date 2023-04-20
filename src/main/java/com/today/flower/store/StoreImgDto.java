package com.today.flower.store;

import org.modelmapper.ModelMapper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreImgDto {
	
	private Long id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private String repImgYn;

    private static ModelMapper modelMapper = new ModelMapper();

    public static StoreImgDto of(StoreImg storeImg) {
        return modelMapper.map(storeImg,StoreImgDto.class);
    }
}