package com.bacancy.ecommerce.service;

import java.util.List;

import com.bacancy.ecommerce.dto.OrderDto;

public interface OrderService {

	OrderDto addOrder(Long userId,Long productId, OrderDto orderDto);

	OrderDto getOrderById(Long id);
	
	OrderDto orderStatus(Long userId,Long orderId,String status);
	
	OrderDto cancelOrder( Long orderId); 

	List<OrderDto> allOrders();

	void deleteOrder(Long id);

}
