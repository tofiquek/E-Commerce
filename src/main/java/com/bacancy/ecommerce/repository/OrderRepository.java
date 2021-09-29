package com.bacancy.ecommerce.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bacancy.ecommerce.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

	@Query("FROM Order WHERE order_date BETWEEN :startDate and  :endDate")
	List<Order> findAllOrderByDateRange(@Param("startDate") Date startDate,@Param("endDate")Date endDate);
	
	
}
