package com.today.flower.storeitem;

import org.modelmapper.ModelMapper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreItemImgDto {
	
	private Long id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private String repImgYn;

    private static ModelMapper modelMapper = new ModelMapper();

    public static StoreItemImgDto of(StoreItemImg storeItemImg) {
        return modelMapper.map(storeItemImg,StoreItemImgDto.class);
    }

}