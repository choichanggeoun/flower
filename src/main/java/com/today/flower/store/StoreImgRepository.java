package com.today.flower.store;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreImgRepository extends JpaRepository<StoreImg, Long> {
	
	List<StoreImg> findByStoreIdOrderByIdAsc(Long storeId);

	StoreImg findByStoreIdAndRepimgYn(Long storeId, String repimgYn);
}
