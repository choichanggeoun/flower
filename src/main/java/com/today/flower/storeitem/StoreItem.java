package com.today.flower.storeitem;


import java.util.List;

import com.today.flower.ItemSellStatus;
import com.today.flower.store.Store;
import com.today.flower.user.SiteUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Table(name="storeitem")
@ToString
public class StoreItem {
	
	@Id
	@Column(name="storeitem_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false, length = 50)
	private String itemNm;
	
	@Column(name="price", nullable = false)
	private int price;
	
	@Column(nullable = false)
	private int stockNumber;
	
	@Lob
	@Column(nullable = false)
	private String itemDetail;

	private ItemSellStatus itemSellStatus;
	
	@ManyToOne
	@JoinColumn(name = "store_id")
	private Store store;
	
	
	public void updateItem(StoreItemFormDto storeItemFormDto) {
		this.itemNm = storeItemFormDto.getItemNm();
		this.price = storeItemFormDto.getPrice();
		this.stockNumber = storeItemFormDto.getStockNumber();
		this.itemDetail = storeItemFormDto.getItemDetail();
		this.itemSellStatus = storeItemFormDto.getItemSellStatus();
	}
	
	public void addStock(int stockNumber){
        this.stockNumber += stockNumber;
    }
}