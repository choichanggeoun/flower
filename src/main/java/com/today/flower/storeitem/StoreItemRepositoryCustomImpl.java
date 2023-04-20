package com.today.flower.storeitem;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.thymeleaf.util.StringUtils;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.today.flower.ItemSellStatus;
import com.today.flower.store.QStore;

import jakarta.persistence.EntityManager;

public class StoreItemRepositoryCustomImpl implements StoreItemRepositoryCustom {

	private JPAQueryFactory queryFactory;
	
	public StoreItemRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}
	
	private BooleanExpression searchSellStatusEq(ItemSellStatus searchSellStatus) {
		return searchSellStatus == null ? null : QStoreItem.storeItem.itemSellStatus.eq(searchSellStatus);
	}
	
	private BooleanExpression searchByLike(String searchBy, String searchQuery) {
		
		if(StringUtils.equals("itemNm", searchBy)) {
			return QStoreItem.storeItem.itemNm.like("%" + searchQuery + "%");
		}
		return null;
	}
	
	@Override
	public Page<StoreItem> getAdminStoreItemPage(StoreItemSearchDto storeItemSearchDto, Pageable pageable){
		QueryResults<StoreItem> results = queryFactory
				.selectFrom(QStoreItem.storeItem)
				.where(searchSellStatusEq(storeItemSearchDto.getSearchSellStatus()),
						searchByLike(storeItemSearchDto.getSearchBy(),
								storeItemSearchDto.getSearchQuery()))
				.orderBy(QStoreItem.storeItem.id.desc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetchResults();
		
		List<StoreItem> content = results.getResults();
		long total = results.getTotal();
		return new PageImpl<>(content, pageable, total);
	}
	
	private BooleanExpression itemNmLike(String searchQuery) {
		return StringUtils.isEmpty(searchQuery) ? null : QStoreItem.storeItem.itemNm.like("%" + searchQuery + "%");
	}
	
	@Override
	public Page<MainStoreItemDto> getMainStoreItemPage(StoreItemSearchDto storeItemSearchDto, Pageable pageable, @PathVariable("storeId") Long storeId){
		
		QStoreItem storeItem = QStoreItem.storeItem;
		QStoreItemImg storeItemImg = QStoreItemImg.storeItemImg;
		QStore store = QStore.store;
		
		QueryResults<MainStoreItemDto> results = queryFactory.select(
				new QMainStoreItemDto(
						storeItem.id,
						storeItem.itemNm,
						storeItem.itemDetail,
						storeItemImg.imgUrl,
						storeItem.price)
						)
				.from(storeItemImg)
				.join(storeItemImg.storeItem, storeItem)
				.join(storeItem.store, store)
				.where(storeItemImg.repimgYn.eq("Y"))
				.where(itemNmLike(storeItemSearchDto.getSearchQuery()))
				.where(store.id.eq(storeId))
				.orderBy(storeItem.id.desc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetchResults();
				
				List<MainStoreItemDto> content = results.getResults();
				long total = results.getTotal();
		return new PageImpl<>(content, pageable, total);
	}
}