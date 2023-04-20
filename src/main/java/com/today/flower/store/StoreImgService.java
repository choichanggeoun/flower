package com.today.flower.store;

import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.today.flower.FileService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreImgService {
	
	@Value("${storeImgLocation}")
	private String storeImgLocation;
	
	private final StoreImgRepository storeImgRepository;
	
	private final FileService fileService;
	
	public void saveStoreImg(StoreImg storeImg, MultipartFile storeImgFile) throws Exception{
		String oriImgName = storeImgFile.getOriginalFilename();
		String imgName = "";
		String imgUrl = "";
		
		//파일업로드
		if(!StringUtils.isEmpty(oriImgName)) {
			imgName = fileService.uploadFile(storeImgLocation, oriImgName, storeImgFile.getBytes());
			imgUrl = "/images/store/" + imgName;
		}
		
		//상품 이미지 정보 저장
		storeImg.updateStoreImg(oriImgName, imgName, imgUrl);
		storeImgRepository.save(storeImg);
	}
	
	public void updateStoreImg(Long storeImgId, MultipartFile storeImgFile)
		throws Exception{
			if(!storeImgFile.isEmpty()) {
				StoreImg savedStoreImg = storeImgRepository.findById(storeImgId)
												 .orElseThrow(EntityNotFoundException::new);
				//기존 이미지 파일 삭제
				if(!StringUtils.isEmpty(savedStoreImg.getImgName())) {
					fileService.deleteFile(storeImgLocation+"/"+savedStoreImg.getImgName());
				}
				String oriImgName = storeImgFile.getOriginalFilename();
				String imgName = fileService.uploadFile(storeImgLocation, oriImgName, storeImgFile.getBytes());
				String imgUrl = "/images/store/" + imgName;
				savedStoreImg.updateStoreImg(oriImgName, imgName, imgUrl);
			}
		}
}