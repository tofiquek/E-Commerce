package com.bacancy.ecommerce.service;

import java.util.List;

import com.bacancy.ecommerce.dto.OrderTrackDto;

public interface OrderTrackService {


	OrderTrackDto addOrderTrack(Long orderId, OrderTrackDto orderTrackDto);

	OrderTrackDto getOrderTrackById(Long id);

	List<OrderTrackDto> allOrdersTrack();

	void deleteOrderTrack(Long id);

}
