package com.bacancy.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bacancy.ecommerce.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

}
