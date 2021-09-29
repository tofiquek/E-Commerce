package com.bacancy.ecommerce.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bacancy.ecommerce.dto.OrderDto;
import com.bacancy.ecommerce.dto.OrderTrackDto;
import com.bacancy.ecommerce.entity.OrderTrack;
import com.bacancy.ecommerce.repository.OrderTrackRepository;
import com.bacancy.ecommerce.service.OrderService;
import com.bacancy.ecommerce.service.OrderTrackService;

@Service
public class OrderTrackServiceImpl implements OrderTrackService{

	@Autowired
	private OrderTrackRepository orderTrackRepository;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ModelMapper modelMapper;

	Logger logger = LoggerFactory.getLogger(OrderTrackServiceImpl.class);
	
	@Override
	public OrderTrackDto addOrderTrack(Long orderId, OrderTrackDto orderTrackDto) {
		logger.info("addOrderTrack Method Started");
		OrderDto orderDto = orderService.getOrderById(orderId);
		orderTrackDto.setOrderDto(orderDto);
		OrderTrack orderTrack = modelMapper.map(orderTrackDto, OrderTrack.class);
		OrderTrack savedOrderTrack = orderTrackRepository.save(orderTrack);
		OrderTrackDto savedOrderTrackDto = modelMapper.map(savedOrderTrack, OrderTrackDto.class);
		logger.info("addOrderTrack Method Ended");
		return savedOrderTrackDto;
	}

	@Override
	public OrderTrackDto getOrderTrackById(Long id) {
		logger.info("getOrderTrackById Method Started");
		Optional<OrderTrack> orderTrackOptional = orderTrackRepository.findById(id);
		OrderTrackDto orderTrackDto = null;
		if (orderTrackOptional.isPresent()) {
			
			orderTrackDto = modelMapper.map(orderTrackOptional.get(), OrderTrackDto.class);
		}
		logger.info("getOrderTrackById Method Ended");
		return orderTrackDto;
	}

	@Override
	public List<OrderTrackDto> allOrdersTrack() {
		logger.info("allOrdersTrack Method Started");
		List<OrderTrack> orderTracks= orderTrackRepository.findAll(); 
		List<OrderTrackDto> orderTracksDto = orderTracks.stream().map(orderTrack -> modelMapper.map(orderTrack, OrderTrackDto.class)).collect(Collectors.toList());
		logger.info("allOrdersTrack Method Ended");
		return orderTracksDto;
	}

	@Override
	public void deleteOrderTrack(Long id) {
		logger.info("deleteOrderTrack Method Started");
		orderTrackRepository.deleteById(id);
		logger.info("deleteOrderTrack Method Ended");
	}
	
}
