package com.jeddi.shoppinglistrest.repository;

import com.jeddi.shoppinglistrest.model.ShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingListRepository extends JpaRepository<ShoppingList, Long> {

    Optional<ShoppingList> findByTitle(String title);

    ShoppingList findTopByOrderByIdDesc();
}
