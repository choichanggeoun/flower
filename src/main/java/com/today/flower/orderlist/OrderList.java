package com.today.flower.orderlist;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.today.flower.config.BaseEntity;
import com.today.flower.user.SiteUser;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="orders")
public class OrderList extends BaseEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderlist_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private SiteUser siteUser;

	private LocalDateTime orderDate; //주문 날짜
	
	@Enumerated(EnumType.STRING)
	private OrderListStatus orderListStatus; //주문 상태 
	
	@OneToMany(mappedBy ="orderList" , cascade = CascadeType.ALL ,
			orphanRemoval = true , fetch = FetchType.LAZY)
	private List<OrderListItem> orderListItems = new ArrayList<>();
	
	public void addOrderItem(OrderListItem orderListItem) {
		orderListItems.add(orderListItem);
		orderListItem.setOrderList(this);
	}
	public static OrderList createOrderList(SiteUser siteUser, List<OrderListItem> orderListItemList) {
		OrderList orderList = new OrderList();
		orderList.setSiteUser(siteUser);
		for( OrderListItem orderItem : orderListItemList ) {
			orderList.addOrderItem(orderItem);
		}
		orderList.setOrderListStatus(OrderListStatus.ORDER);
		orderList.setOrderDate(LocalDateTime.now());
		return orderList;
	}
	
	public int getTotalPrice() {
		int totalPrice = 0;
		for(OrderListItem orderListItem : orderListItems) {
			totalPrice += orderListItem.getTotalPrice();
		}
		return totalPrice;
	}
	 public void cancelOrder() {
	        this.orderListStatus = OrderListStatus.CANCEL;
	        for (OrderListItem orderlistItem : orderListItems) {
	            orderlistItem.cancel();
	        }
	    }
	
}