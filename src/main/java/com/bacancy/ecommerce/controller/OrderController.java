package com.bacancy.ecommerce.controller;


import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bacancy.ecommerce.dto.OrderDto;
import com.bacancy.ecommerce.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private HttpServletRequest request;
	
	Logger logger = LoggerFactory.getLogger(OrderController.class);
	
	@GetMapping
	public ResponseEntity<List<OrderDto>> findAllOrders(){
		logger.info("findAllOrders method started & Request URI = "+ request.getRequestURI());
		List<OrderDto> orders = orderService.allOrders();
		ResponseEntity responseEntity = new ResponseEntity(orders, HttpStatus.OK);
		logger.info("findAllOrders method Ended & Response Generated");
		return responseEntity;
	}
	
	@GetMapping("/{startDate}/{endDate}")
	public ResponseEntity<List<OrderDto>> findAllOrdersByRange(@PathVariable(name="startDate")Date startDate,@PathVariable(name = "endDate") Date endDate){
		logger.info("findAllOrdersByRange method started & Request URI = "+ request.getRequestURI());
		List<OrderDto> orders = orderService.allOrdersByRange(startDate, endDate);
		
		ResponseEntity responseEntity = new ResponseEntity(orders, HttpStatus.OK);
		logger.info("findAllOrdersByRange method Ended & Response Generated");
		return responseEntity;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<OrderDto> findOrder(@PathVariable(name = "id") Long id) {
		logger.info("findOrder method started & Request URI = "+ request.getRequestURI());
		ResponseEntity responseEntity = new ResponseEntity( orderService.getOrderById(id),HttpStatus.OK);
		logger.info("findOrder method Ended & Response Generated");
		return responseEntity;
	}
	
	@PostMapping("/{userId}/{productId}")
	public ResponseEntity<OrderDto> saveOrder(@PathVariable(name = "userId") Long userId,@PathVariable(name = "productId") Long productId,@RequestBody OrderDto orderDto) {
		logger.info("saveOrder method started & Request URI = "+ request.getRequestURI());
		ResponseEntity responseEntity = new ResponseEntity(orderService.addOrder(userId, productId, orderDto), HttpStatus.OK);
		logger.info("saveOrder method Ended & Response Generated");
		return responseEntity ;
		
	}
	
	@PutMapping("/{userId}/{orderId}/{status}")
	public ResponseEntity<OrderDto> updateOrder(@PathVariable(name = "userId") Long userId,@PathVariable(name = "orderId") Long orderId,@PathVariable(name = "status") String  status) {
		logger.info("updateOrder method started & Request URI = "+ request.getRequestURI());
		ResponseEntity responseEntity = new ResponseEntity(orderService.orderStatus(userId, orderId, status), HttpStatus.OK);
		logger.info("updateOrder method Ended & Response Generated");
		return responseEntity ;
		
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<OrderDto> deleteOrder(@PathVariable(name="id") Long id) {
		logger.info("deleteOrder method started & Request URI = "+ request.getRequestURI());
		ResponseEntity responseEntity = new ResponseEntity(orderService.cancelOrder(id), HttpStatus.OK);
		logger.info("deleteOrder method ended & response generated");
		return responseEntity ; 
	}

}
