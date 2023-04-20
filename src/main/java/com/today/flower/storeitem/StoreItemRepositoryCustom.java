package com.today.flower.storeitem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;

public interface StoreItemRepositoryCustom {
	
	Page<StoreItem> getAdminStoreItemPage(StoreItemSearchDto storeItemSearchDto, Pageable pageable);

	Page<MainStoreItemDto> getMainStoreItemPage(StoreItemSearchDto itemSearchDto, Pageable pageable, @PathVariable("storeId") Long storeId);
}
