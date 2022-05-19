package com.jeddi.shoppinglistrest.repository;

import com.jeddi.shoppinglistrest.model.ProductDetail;
import com.jeddi.shoppinglistrest.model.ProductDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, ProductDetailId> {
}
