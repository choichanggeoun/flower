package com.today.flower.basket;

import com.today.flower.config.BaseEntity;
import com.today.flower.user.SiteUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Getter
@Table(name = "bakset")
@Setter
@Entity
public class Basket extends BaseEntity{
	
	@Id
	@Column(name="basket_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="siteuser_id")
    private SiteUser siteUser;

    public static Basket createBasket(SiteUser siteUser){
         Basket basket = new Basket();
        basket.setSiteUser(siteUser);
        return basket;
	
}
}
