package com.today.flower.store;


import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class StoreService {
	
	private final StoreRepository storeRepository;
	private final StoreImgRepository storeImgRepository;
	private final StoreImgService storeImgService;
	
	public List<Store> getList(){
		return this.storeRepository.findAll();
	}
	
	
	public void create(String storeName, String storeAddr, String openTime, String minAmount, String deliveryTips, String freeDelivery, String pickupTime) {
        Store s = new Store();
        s.setStoreName(storeName);
        s.setStoreAddr(storeAddr);
        s.setOpenTime(openTime);
        s.setMinAmount(minAmount);
        s.setDeliveryTips(deliveryTips);
        s.setFreeDelivery(freeDelivery);
        s.setPickupTime(pickupTime);
        this.storeRepository.save(s);
    }
	
	@Transactional(readOnly = true)
    public StoreFormDto getStoreDtl(Long storeId){
		List<StoreImg> storeImgList = storeImgRepository.findByStoreIdOrderByIdAsc(storeId);
        List<StoreImgDto> storeImgDtoList = new ArrayList<>();
        for (StoreImg storeImg : storeImgList) {
            StoreImgDto storeImgDto = StoreImgDto.of(storeImg);
            storeImgDtoList.add(storeImgDto);
        }
        Store store = storeRepository.findById(storeId)
                .orElseThrow(EntityNotFoundException::new);
        StoreFormDto storeFormDto = StoreFormDto.of(store);
        storeFormDto.setStoreImgDtoList(storeImgDtoList);
        return storeFormDto;
    }
	
	public Long saveStore(StoreFormDto storeFormDto, List<MultipartFile> storeImgFileList) throws Exception{
		//상품등록
		Store store = storeFormDto.createStore();
		storeRepository.save(store);
		//이미지등록
		for(int i=0; i<storeImgFileList.size(); i++) {
			StoreImg storeImg = new StoreImg();
			storeImg.setStore(store);
			if(i == 0) {
				storeImg.setRepimgYn("Y");
			}else {
				storeImg.setRepimgYn("N");
			}
			storeImgService.saveStoreImg(storeImg, storeImgFileList.get(i));
		}
		return store.getId();
	}
	
	public Long updateStore(StoreFormDto storeFormDto, List<MultipartFile> storeImgFileList) throws Exception{
		
		//상품수정
		Store store = storeRepository.findById(storeFormDto.getId())
				      .orElseThrow(EntityNotFoundException::new);
		store.updateStore(storeFormDto);
		
		List<Long> storeImgIds = storeFormDto.getStoreImgIds();
				
		//이미지 등록
		for(int i=0;i<storeImgFileList.size();i++) {
			storeImgService.updateStoreImg(storeImgIds.get(i), storeImgFileList.get(i));
		}
		return store.getId();
	}
	
	@Transactional(readOnly = true)
	public Page<Store> getStorePage(StoreSearchDto storeSearchDto, Pageable pageable){
		return storeRepository.getStorePage(storeSearchDto, pageable);
	}
	
	@Transactional(readOnly = true)
	public Page<MainStoreDto> getMainStorePage(StoreSearchDto storeSearchDto, Pageable pageable){
		return storeRepository.getMainStorePage(storeSearchDto, pageable);
	}
}