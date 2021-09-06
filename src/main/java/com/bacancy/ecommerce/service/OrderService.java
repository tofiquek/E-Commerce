package com.bacancy.ecommerce.service;

import java.util.List;

import com.bacancy.ecommerce.dto.OrderDto;
import com.bacancy.ecommerce.dto.ProductDto;

public interface OrderService {

	OrderDto addOrder(Long userId,Long productId, OrderDto orderDto);

	OrderDto getOrderById(Long id);

	List<OrderDto> allOrders();

	void deleteOrder(Long id);

}
