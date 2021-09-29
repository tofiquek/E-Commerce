package com.bacancy.ecommerce.service.impl;

import java.time.LocalDate;
import java.util.Date;
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
import com.bacancy.ecommerce.dto.ProductDto;
import com.bacancy.ecommerce.dto.UserDto;
import com.bacancy.ecommerce.entity.Order;
import com.bacancy.ecommerce.exception.NotAccessAbleException;
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
	
	Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	public final String PLACED = "Placed";
	public final String CONFIRM = "Confirm";
	public final String CANCEL = "Cancel";
	public final String DILIVERED = "Dilivered";

	@Override
	public OrderDto addOrder(Long userId, Long productId, OrderDto orderDto) {
		logger.info("addOrder Method Started");
		UserDto userDto = userService.getUserById(userId);
		ProductDto productDto = productService.getProductById(productId);
		orderDto.setUser(userDto);
		orderDto.setProduct(productDto);
		orderDto.setAmount(productDto.getPrice()*orderDto.getQuantity());
		orderDto.setOrderDate(LocalDate.now());
		System.out.println(orderDto.getOrderDate());
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
		orderTrackService.addOrderTrack(savedOrderDto.getId(), orderTrackDto);
		}
		logger.info("addOrder Method Ended");
		return savedOrderDto;
	}
	
	

	@Override
	public OrderDto getOrderById(Long id) {
		logger.info("getOrderById Method Started");
		Optional<Order> orderOptional = orderRepository.findById(id);
		if(!orderOptional.isPresent()) {
			logger.error("Order by Id = "+id+" is not found");
			throw new OrderNotFoundException("Order not Found Exception by id "+id);
		}
		OrderDto	orderDto = modelMapper.map(orderOptional.get(), OrderDto.class);
		logger.info("getOrderById Method Ended");
		return orderDto;
	}

	@Override
	public List<OrderDto> allOrders() {
		logger.info("allOrders Method Started");
		List<Order> orders= orderRepository.findAll(); 
		List<OrderDto> ordersDto = orders.stream().map(order -> modelMapper.map(order, OrderDto.class)).collect(Collectors.toList());
		logger.info("allOrders Method Ended");
		return ordersDto;
	}

	@Override
	public void deleteOrder(Long id) {
		logger.info("deleteOrder Method Started");
		orderRepository.deleteById(id);
		logger.info("deleteOrder Method Started");
	}



	@Override
	public OrderDto orderStatus(Long userId, Long orderId, String status) {
		logger.info("orderStatus Method Started");
		UserDto userDto = userService.getUserById(userId);
		if(userDto.getRoleId()!=0) {
			throw new NotAccessAbleException("Only admin can Confirm or Dilivered the Order");
		}
		OrderDto orderDto = getOrderById(orderId);
		if(status.equalsIgnoreCase(CONFIRM)) {
			orderDto.setStatus(CONFIRM);
		}
		if(orderDto.getStatus().equalsIgnoreCase(CONFIRM) && status.equalsIgnoreCase(DILIVERED) ) {
			orderDto.setStatus(DILIVERED);
		}
		Order order = modelMapper.map(orderDto, Order.class);
		Order savedOrder = orderRepository.save(order);
		OrderDto savedOrderDto = modelMapper.map(savedOrder, OrderDto.class);
		if(savedOrder!=null) {
		OrderTrackDto orderTrackDto = new OrderTrackDto();
		orderTrackDto.setStatus(savedOrderDto.getStatus());
		orderTrackDto.setDate(LocalDate.now());
		orderTrackService.addOrderTrack(savedOrderDto.getId(), orderTrackDto);
		}
		logger.info("orderStatus Method Ended");
		return savedOrderDto;
	}



	@Override
	public OrderDto cancelOrder( Long orderId) {
		logger.info("cancelOrder Method Started");
		OrderDto orderDto = getOrderById(orderId);
			orderDto.setStatus(CANCEL);
		ProductDto productDto = orderDto.getProduct();
		Order order = modelMapper.map(orderDto, Order.class);
		Order savedOrder = orderRepository.save(order);
		OrderDto savedOrderDto = modelMapper.map(savedOrder, OrderDto.class);
		if(savedOrder!=null) {
			productDto.setTotalStock(productDto.getTotalStock()+orderDto.getQuantity());
			productService.updateProduct(productDto);
			OrderTrackDto orderTrackDto = new OrderTrackDto();
			orderTrackDto.setStatus(CANCEL);
			orderTrackDto.setDate(LocalDate.now());
			orderTrackService.addOrderTrack(savedOrderDto.getId(), orderTrackDto);
		}
		logger.info("cancelOrder Method Ended");
		return savedOrderDto;
	
	}
	
	@Override
	public List<OrderDto> allOrdersByRange(Date startDate,Date endDate) {
		List<Order> orders= orderRepository.findAllOrderByDateRange(startDate, endDate); 
		List<OrderDto> ordersDto = orders.stream().map(order -> modelMapper.map(order, OrderDto.class)).collect(Collectors.toList());
		return ordersDto;
	}

}
