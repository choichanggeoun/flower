package com.today.flower.orderlist;

import com.today.flower.config.BaseEntity;
import com.today.flower.storeitem.StoreItem;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OrderListItem extends BaseEntity {

	@Id
	@GeneratedValue
	@Column(name = "order_item_id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "storeitem_id")
	private StoreItem storeItem;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orderlist_id")
	private OrderList orderList;
	
	private int orderPrice;
	
	private int count;
	
	public static OrderListItem createOrderListItem(StoreItem storeItem, int count) {
		OrderListItem orderItem = new OrderListItem();
		orderItem.setCount(count);
		orderItem.setOrderPrice(storeItem.getPrice());
	
		
		return orderItem;
	}
	
	public int getTotalPrice() {
		return orderPrice * count;
	}
	
	public void cancel() {
	        this.getStoreItem().addStock(count);
	    }
}



