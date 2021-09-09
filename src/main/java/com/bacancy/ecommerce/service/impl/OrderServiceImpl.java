package com.bacancy.ecommerce.service.impl;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bacancy.ecommerce.dto.OrderDto;
import com.bacancy.ecommerce.dto.OrderTrackDto;
import com.bacancy.ecommerce.dto.ProductDto;
import com.bacancy.ecommerce.dto.UserDto;
import com.bacancy.ecommerce.entity.Order;
import com.bacancy.ecommerce.exception.OrderNotFoundException;
import com.bacancy.ecommerce.repository.OrderRepository;
import com.bacancy.ecommerce.service.OrderService;
import com.bacancy.ecommerce.service.OrderTrackService;
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
	private OrderTrackService orderTrackService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public final String PLACED = "Placed";
	public final String CONFIRM = "Confirm";
	public final String CANCEL = "Cancel";
	public final String DILIVERED = "Dilivered";

	@Override
	public OrderDto addOrder(Long userId, Long productId, OrderDto orderDto) {
		UserDto userDto = userService.getUserById(userId);
		ProductDto productDto = productService.getProductById(productId);
		orderDto.setUser(userDto);
		orderDto.setProduct(productDto);
		orderDto.setAmount(productDto.getPrice()*orderDto.getQuantity());
		orderDto.setOrderDate(new Date());
		orderDto.setStatus(PLACED);
		Order order = modelMapper.map(orderDto, Order.class);
		Order savedOrder = orderRepository.save(order);
		OrderDto savedOrderDto = modelMapper.map(savedOrder, OrderDto.class);
		if(savedOrder!=null) {
		productDto.setTotalStock(productDto.getTotalStock()-orderDto.getQuantity());
		productService.updateProduct(productDto);
		OrderTrackDto orderTrackDto = new OrderTrackDto();
		orderTrackDto.setStatus(PLACED);
		orderTrackDto.setDate(LocalDate.now());
		orderTrackService.addOrder(savedOrderDto.getId(), orderTrackDto);
		}
		return savedOrderDto;
	}

	@Override
	public OrderDto getOrderById(Long id) {
		Optional<Order> orderOptional = orderRepository.findById(id);
		if(!orderOptional.isPresent()) {
			throw new OrderNotFoundException("Order not Found Exception by id "+id);
		}
		OrderDto	orderDto = modelMapper.map(orderOptional.get(), OrderDto.class);
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
