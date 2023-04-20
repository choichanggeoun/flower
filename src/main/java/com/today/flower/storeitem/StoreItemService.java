package com.today.flower.storeitem;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreItemService {
	
	private final StoreItemRepository storeItemRepository;
	private final StoreItemImgService storeItemImgService;
	private final StoreItemImgRepository storeItemImgRepository;

	
	public Long saveStoreItem(StoreItemFormDto storeItemFormDto, List<MultipartFile> storeItemImgFileList) throws Exception{
		//상품등록
		StoreItem storeItem = storeItemFormDto.createItem();
		storeItemRepository.save(storeItem);
		//이미지등록
		for(int i=0; i<storeItemImgFileList.size(); i++) {
			StoreItemImg storeItemImg = new StoreItemImg();
			storeItemImg.setStoreItem(storeItem);
			if(i == 0) {
				storeItemImg.setRepimgYn("Y");
			}else {
				storeItemImg.setRepimgYn("N");
			}
			storeItemImgService.saveStoreItemImg(storeItemImg, storeItemImgFileList.get(i));
		}
		return storeItem.getId();
	}

    @Transactional(readOnly = true)
    public StoreItemFormDto getItemDtl(Long storeItemId){
        List<StoreItemImg> storeItemImgList = storeItemImgRepository.findByStoreItemIdOrderByIdAsc(storeItemId);
        List<StoreItemImgDto> storeItemImgDtoList = new ArrayList<>();
        for (StoreItemImg storeItemImg : storeItemImgList) {
        	StoreItemImgDto storeItemImgDto = StoreItemImgDto.of(storeItemImg);
            storeItemImgDtoList.add(storeItemImgDto);
        }

        StoreItem storeItem = storeItemRepository.findById(storeItemId)
                .orElseThrow(EntityNotFoundException::new);
        StoreItemFormDto storeItemFormDto = StoreItemFormDto.of(storeItem);
        storeItemFormDto.setStoreItemImgDtoList(storeItemImgDtoList);
        return storeItemFormDto;
    }
    
    public Long updateItem(StoreItemFormDto storeItemFormDto, List<MultipartFile> storeItemImgFileList) throws Exception{
    	
    	//상품 수정
    	StoreItem storeItem = storeItemRepository.findById(storeItemFormDto.getId())
    				         .orElseThrow(EntityNotFoundException::new);
    	storeItem.updateItem(storeItemFormDto);
    	
    	List<Long> storeItemImgIds = storeItemFormDto.getStoreItemImgIds();
    	
    	//이미지 등록
    	for(int i=0;i<storeItemImgFileList.size();i++) {
    		storeItemImgService.updateItemImg(storeItemImgIds.get(i), storeItemImgFileList.get(i));
    	}
    	return storeItem.getId();
    }
    
    @Transactional(readOnly = true)
    public Page<StoreItem> getAdminStoreItemPage(StoreItemSearchDto storeItemSearchDto, Pageable pageable){
    	return storeItemRepository.getAdminStoreItemPage(storeItemSearchDto, pageable);
    }
    
    @Transactional(readOnly = true)
    public Page<MainStoreItemDto> getMainStoreItemPage(StoreItemSearchDto storeItemSearchDto, Pageable pageable, @PathVariable("storeId") Long storeId){
    	return storeItemRepository.getMainStoreItemPage(storeItemSearchDto, pageable, storeId);
    }

}
