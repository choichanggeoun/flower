package com.today.flower.store;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreForm {
	@NotEmpty(message="업체명은 필수항목입니다.")
    @Size(max=200)
    private String storeName;

    @NotEmpty(message="업체주소는 필수항목입니다.")
    private String storeAddr;
    
    @NotEmpty(message="영업시간은 필수항목입니다.")
    private String openTime;
    
    @NotEmpty(message="최소주문금액은 필수항목입니다.")
    private String minAmount;
    
    @NotEmpty(message="배달팁은 필수항목입니다.")
    private String deliveryTips;
    
    @NotEmpty(message="무료배달금액은 필수항목입니다.")
    private String freeDelivery;
    
    @NotEmpty(message="픽업시간은 필수항목입니다.")
    private String pickupTime;
   
	
}
