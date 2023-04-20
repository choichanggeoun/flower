package com.today.flower.basket;

import com.today.flower.storeitem.StoreItem;
import com.today.flower.basket.BasketItem;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name ="basket_item")
public class BasketItem {

	
	@Id
	@GeneratedValue
	@Column(name = "basket_item_id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="basket_id")
	private Basket basket;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="item_id")
	private StoreItem storeItem;
	
	private int count;
	
	public static BasketItem createBasketItem(Basket basket, StoreItem storeItem, int count) {
		BasketItem basketItem = new BasketItem();
		basketItem.setBasket(basket);
	        basketItem.setStoreItem(storeItem);
	        basketItem.setCount(count);
	        return basketItem;
	    }

    public void addCount(int count){
        this.count += count;
    }

    public void updateCount(int count){
        this.count = count;
    }
	
}
