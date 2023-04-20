package com.today.flower.orderlist;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderListRepository extends JpaRepository<OrderList,Long> {

	   @Query("select o from OrderList o " +
	            "where o.siteUser.email = :email " +
	            "order by o.orderDate desc"
	    )
	    List<OrderList> findOrderList(@Param("email") String email, Pageable pageable);

	    @Query("select count(o) from OrderList o " +
	            "where o.siteUser.email = :email"
	    )
	    Long countOrder(@Param("email") String email);
}
