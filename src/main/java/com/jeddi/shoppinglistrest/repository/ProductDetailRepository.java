package com.jeddi.shoppinglistrest.repository;

import com.jeddi.shoppinglistrest.model.ProductDetail;
import com.jeddi.shoppinglistrest.model.ProductDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, ProductDetailId> {

    @Modifying
    @Query(value = "DELETE FROM product_detail WHERE shoppinglist_id = :id", nativeQuery = true)
    void deleteByShoppinglist_id(@Param("id") long shoppinglist_id);

}
