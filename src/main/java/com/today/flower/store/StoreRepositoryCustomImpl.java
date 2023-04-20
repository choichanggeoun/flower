package com.today.flower.store;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

public class StoreRepositoryCustomImpl implements StoreRepositoryCustom {
	
	private JPAQueryFactory queryFactory;
	
	public StoreRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}
	
	private BooleanExpression searchByLike(String searchBy, String searchQuery) {
		if(StringUtils.equals("storeName", searchBy)) {
			return QStore.store.storeName.like("%" + searchQuery + "%");
		}else if(StringUtils.equals("storeAddr", searchBy)) {
			return QStore.store.storeAddr.like("%" + searchQuery + "%");
		}
		return null;
	}
	
	@Override
	public Page<Store> getStorePage(StoreSearchDto storeSearchDto, Pageable pageable){
		QueryResults<Store> results = queryFactory
				.selectFrom(QStore.store)
				.where(searchByLike(storeSearchDto.getSearchBy(),
						storeSearchDto.getSearchQuery()))
				.orderBy(QStore.store.id.desc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetchResults();
		
		List<Store> content = results.getResults();
		long total = results.getTotal();
		return new PageImpl<>(content, pageable, total);
						
	}
	
	private BooleanExpression storeNameLike(String searchQuery) {
		return StringUtils.isEmpty(searchQuery) ?
				null : QStore.store.storeName.like("%" + searchQuery + "%");
	}
	
	@Override
	public Page<MainStoreDto> getMainStorePage(StoreSearchDto storeSearchDto, Pageable pageable){
		QStore store = QStore.store;
		QStoreImg storeImg = QStoreImg.storeImg;
		
		QueryResults<MainStoreDto> results = queryFactory.select(new QMainStoreDto(store.id,store.storeName,store.storeAddr,store.openTime,storeImg.imgUrl))
											.from(storeImg)
											.join(storeImg.store, store)
											.where(storeImg.repimgYn.eq("Y"))
											.where(storeNameLike(storeSearchDto.getSearchQuery()))
											.orderBy(store.id.desc())
											.offset(pageable.getOffset())
											.limit(pageable.getPageSize())
											.fetchResults();
		
		List<MainStoreDto> content = results.getResults();
		long total = results.getTotal();
		return new PageImpl<>(content, pageable, total);
	}
}