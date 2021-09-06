package com.bacancy.ecommerce.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "quantity")
	private int quantity;
	
	@Column(name = "shipping_address")
	private String shippingAddress;
	
	@Column(name = "amount")
	private double amount;
	
	@Column(name = "payment")
	private String payment;
	
	@Column(name = "order_date")
	private Date orderDate;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getShippingAddress() {
		return shippingAddress;
	}
	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
