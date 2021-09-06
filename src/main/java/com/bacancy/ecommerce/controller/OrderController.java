package com.bacancy.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bacancy.ecommerce.dto.CategoryDto;
import com.bacancy.ecommerce.dto.OrderDto;
import com.bacancy.ecommerce.dto.ProductDto;
import com.bacancy.ecommerce.dto.UserDto;
import com.bacancy.ecommerce.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@GetMapping
	public ResponseEntity<List<OrderDto>> findAllOrders(){
		List<OrderDto> orders = orderService.allOrders();
		return new ResponseEntity(orders, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<OrderDto> findOrder(@PathVariable(name = "id") Long id) {
		return new ResponseEntity( orderService.getOrderById(id),HttpStatus.OK);
	}
	
	@PostMapping("/{userId}/{productId}")
	public ResponseEntity<OrderDto> saveOrder(@PathVariable(name = "userId") Long userId,@PathVariable(name = "productId") Long productId,@RequestBody OrderDto orderDto) {
		return new ResponseEntity(orderService.addOrder(userId, productId, orderDto), HttpStatus.OK) ;
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity deleteOrder(@PathVariable(name="id") Long id) {
		orderService.deleteOrder(id);
		return new ResponseEntity(HttpStatus.OK); 
	}

}
