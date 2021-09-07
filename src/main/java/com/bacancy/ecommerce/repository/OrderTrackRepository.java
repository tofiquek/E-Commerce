package com.bacancy.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bacancy.ecommerce.entity.OrderTrack;

@Repository
public interface OrderTrackRepository extends JpaRepository<OrderTrack, Long>{

}
