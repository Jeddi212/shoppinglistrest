package com.jeddi.shoppinglistrest.service;

import com.jeddi.shoppinglistrest.dto.ShoppingListDto;
import com.jeddi.shoppinglistrest.model.ProductDetail;
import com.jeddi.shoppinglistrest.model.ShoppingList;
import com.jeddi.shoppinglistrest.repository.ProductDetailRepository;
import com.jeddi.shoppinglistrest.repository.ShoppingListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ShoppingListService {

    private final ShoppingListRepository slRepo;
    private final ProductDetailRepository pdRepo;
    private final MapperService mpSrvc;

    @Autowired
    public ShoppingListService(ShoppingListRepository shRepo, ProductDetailRepository pdRepo, MapperService mpSrvc) {
        this.slRepo = shRepo;
        this.pdRepo = pdRepo;
        this.mpSrvc = mpSrvc;
    }

    public List<ShoppingList> getShoppingLists() {
        return slRepo.findAll();
    }

    public Optional<ShoppingList> getShoppingListById(Long shoppinglist_id) {
        return slRepo.findById(shoppinglist_id);
    }

    @Transactional
    public void postShoppingList(ShoppingListDto slDto) {
        if (slDto.getTitle() == null || "".equals(slDto.getTitle())) {
            throw new IllegalStateException("Shopping List Title can't be empty");
        }

        Optional<ShoppingList> sl = slRepo.findByTitle(slDto.getTitle());
        if (sl.isPresent()) {
            throw new IllegalStateException("Shopping List Title is taken");
        }

        // Map Shopping List DTO to Model
        ShoppingList shoppingList = mpSrvc.mapShoppingList(slDto.getTitle(), null);

        slRepo.save(shoppingList);

        // Map Product Detail DTO to Model
        if (slDto.getProductList() != null) {
            List<ProductDetail> productDetails;
            if (!slDto.getProductList().isEmpty()) {
                productDetails = mpSrvc.mapProductDetail(shoppingList.getId(), slDto.getProductList());
                pdRepo.saveAll(productDetails);
            }
        }
    }

    public long lastShoppingListId()
    {
        // Get the last id from Shopping List table
        ShoppingList shoppingList = slRepo.findTopByOrderByIdDesc();

        if (shoppingList == null) {
            return 1;
        }
        return shoppingList.getId() + 1;
    }
}
