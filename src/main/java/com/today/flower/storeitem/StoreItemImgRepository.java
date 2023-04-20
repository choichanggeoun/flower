package com.today.flower.storeitem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreItemImgRepository extends JpaRepository<StoreItemImg, Long> {
	
	List<StoreItemImg> findByStoreItemIdOrderByIdAsc(Long storeItemId);
	
	StoreItemImg findByStoreItemIdAndRepimgYn(Long storeItemId, String repimgYn);
}