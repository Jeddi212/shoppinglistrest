package com.jeddi.shoppinglistrest.controller;

import com.jeddi.shoppinglistrest.dto.ShoppingListDto;
import com.jeddi.shoppinglistrest.model.ShoppingList;
import com.jeddi.shoppinglistrest.service.ShoppingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "shoppinglist")
public class ShoppingListController {
    private final ShoppingListService shSrvc;

    @Autowired
    public ShoppingListController(ShoppingListService shSrvc) {
        this.shSrvc = shSrvc;
    }

    @GetMapping
    public List<ShoppingList> getShoppingLists() {
        return shSrvc.getShoppingLists();
    }

    @GetMapping
    @RequestMapping(path = "byid")
    public Optional<ShoppingList> getShoppingList(
            @RequestParam(name = "id") Long shoppinglist_id) {
        return shSrvc.getShoppingListById(shoppinglist_id);
    }

    @PostMapping
    public void postShoppingList(@RequestBody ShoppingListDto shoppingList) {
        shSrvc.postShoppingList(shoppingList);
    }

    @PutMapping(path = "{shoppinglistid}")
    public void updateShoppingList(
            @PathVariable("shoppinglistid") Long shoppinglist_id,
            @RequestBody ShoppingListDto shoppingList) {
        shSrvc.updateShoppingList(shoppinglist_id, shoppingList);
    }

    @DeleteMapping(path = "{shoppinglistid}")
    public void deleteShoppingList(@PathVariable("shoppinglistid") Long shoppinglist_id) {
        shSrvc.deleteShoppingListById(shoppinglist_id);
    }
}
