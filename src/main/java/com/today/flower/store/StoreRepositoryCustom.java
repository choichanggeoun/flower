package com.today.flower.store;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StoreRepositoryCustom {
	
	Page<Store> getStorePage(StoreSearchDto storeSearchDto, Pageable pageable);
	
	Page<MainStoreDto> getMainStorePage(StoreSearchDto storeSearchDto, Pageable pageable);
	

}
