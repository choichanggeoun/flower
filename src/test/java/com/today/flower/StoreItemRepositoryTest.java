package com.today.flower;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.today.flower.storeitem.StoreItem;
import com.today.flower.storeitem.StoreItemRepository;

@SpringBootTest
@TestPropertySource(locations="classpath:application.properties")
public class StoreItemRepositoryTest {
	
	@Autowired
	StoreItemRepository storeItemRepository;
	
	@Test
	@DisplayName("상품 저장 테스트")
	public void createItemTest() {
		StoreItem storeItem = new StoreItem();
		
		
		storeItem.setPrice(10000);
		storeItem.setItemDetail("테스트 상품 상세 설명");
		storeItem.setItemSellStatus(ItemSellStatus.SELL);
		storeItem.setStockNumber(100);
		StoreItem savedItem = storeItemRepository.save(storeItem);
		System.out.println(savedItem.toString());
	}
}