package com.today.flower.basket;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BasketOrderDto {

    private Long basketItemId;

    private List<BasketOrderDto> basketOrderDtoList;
}