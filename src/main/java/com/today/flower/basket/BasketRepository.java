package com.today.flower.basket;

import org.springframework.data.jpa.repository.JpaRepository;

public interface  BasketRepository extends JpaRepository<Basket,Long> {

	Basket findBySiteUserId(Long siteUserId);
	
}
