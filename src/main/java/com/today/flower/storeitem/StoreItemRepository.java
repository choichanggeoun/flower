package com.today.flower.storeitem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface StoreItemRepository extends JpaRepository<StoreItem, Long>,
QuerydslPredicateExecutor<StoreItem>, StoreItemRepositoryCustom{
	

}
