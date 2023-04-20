package com.today.flower.store;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface StoreRepository extends JpaRepository<Store, Long>,
QuerydslPredicateExecutor<Store>, StoreRepositoryCustom{
	

}