package com.jeddi.shoppinglistrest.service;

import com.jeddi.shoppinglistrest.dto.ProductDetailDto;
import com.jeddi.shoppinglistrest.model.ProductDetail;
import com.jeddi.shoppinglistrest.model.ProductDetailId;
import com.jeddi.shoppinglistrest.model.ShoppingList;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Service
public class MapperService {

    public ShoppingList mapShoppingListForUpdate(long shoppingListId, String title, List<ProductDetail> productDetails) {
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setId(shoppingListId);
        shoppingList.setTitle(title);
        shoppingList.setDateTime(LocalDateTime.now().withNano(0));
        shoppingList.setProductList(productDetails);
        return shoppingList;
    }

    public ShoppingList mapShoppingListForUpdate(String title, List<ProductDetail> productDetails) {
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setTitle(title);
        shoppingList.setDateTime(LocalDateTime.now().withNano(0));
        shoppingList.setProductList(productDetails);
        return shoppingList;
    }

    public ShoppingList mapShoppingListForUpdate(ShoppingList shoppingList, String title) {
        shoppingList.setTitle(title);
        return shoppingList;
    }

    public List<ProductDetail> mapProductDetail(long shoppingListId, List<ProductDetailDto> productList) {
        List<ProductDetail> productDetails = new LinkedList<>();
        int counter = 1;
        for (ProductDetailDto productDetailDto : productList) {
            productDetails.add(
                new ProductDetail(
                    new ProductDetailId(shoppingListId, counter),
                    productDetailDto.getProductName(),
                    productDetailDto.getQuantity(),
                    productDetailDto.getMemo()
                )
            );
            counter++;
        }
        return productDetails;
    }
}
