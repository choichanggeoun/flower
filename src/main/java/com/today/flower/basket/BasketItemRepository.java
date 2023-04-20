package com.today.flower.basket;

import java.util.List;

import com.today.flower.basket.BasketItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BasketItemRepository extends JpaRepository<BasketItem,Long> {

	  BasketItem findByBasketIdAndStoreItemId(Long basketId, Long storeItemId);

	    @Query("select new com.today.flower.basket.BasketDetailDto(ci.id, i.itemNm, i.price, ci.count, im.imgUrl) " +
	            "from BasketItem ci, StoreItemImg im " +
	            "join ci.storeItem i " +
	            "where ci.basket.id = :BasketId " +
	            "and im.storeItem.id = ci.basket.id " +
	            "and im.repimgYn = 'Y' " +
	            "order by ci.basket.id desc"
	            )
	    List<BasketDetailDto> findBasketDetailDtoList(Long basketId);

	}

