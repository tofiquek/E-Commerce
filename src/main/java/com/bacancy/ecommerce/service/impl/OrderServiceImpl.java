package com.bacancy.ecommerce.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bacancy.ecommerce.dto.OrderDto;
import com.bacancy.ecommerce.dto.ProductDto;
import com.bacancy.ecommerce.dto.UserDto;
import com.bacancy.ecommerce.entity.Order;
import com.bacancy.ecommerce.repository.OrderRepository;
import com.bacancy.ecommerce.service.OrderService;
import com.bacancy.ecommerce.service.ProductService;
import com.bacancy.ecommerce.service.UserService;

@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public OrderDto addOrder(Long userId, Long productId, OrderDto orderDto) {
		UserDto userDto = userService.getUserById(userId);
		ProductDto productDto = productService.getProductById(productId);
		orderDto.setUserDto(userDto);
		orderDto.setProductDto(productDto);
		orderDto.setAmount(productDto.getPrice()*orderDto.getQuantity());
		orderDto.setOrderDate(new Date());
		Order order = modelMapper.map(orderDto, Order.class);
		Order savedOrder = orderRepository.save(order);
		OrderDto savedOrderDto = modelMapper.map(savedOrder, OrderDto.class);
		return savedOrderDto;
	}

	@Override
	public OrderDto getOrderById(Long id) {
		Optional<Order> orderOptional = orderRepository.findById(id);
		OrderDto orderDto = null;
		if (orderOptional.isPresent()) {
			
			orderDto = modelMapper.map(orderOptional.get(), OrderDto.class);
		}
		return orderDto;
	}

	@Override
	public List<OrderDto> allOrders() {
		List<Order> orders= orderRepository.findAll(); 
		List<OrderDto> ordersDto = orders.stream().map(order -> modelMapper.map(order, OrderDto.class)).collect(Collectors.toList());
		return ordersDto;
	}

	@Override
	public void deleteOrder(Long id) {
		orderRepository.deleteById(id);
	}

}
