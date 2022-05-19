package com.jeddi.shoppinglistrest.service;

import com.jeddi.shoppinglistrest.dto.ShoppingListDto;
import com.jeddi.shoppinglistrest.model.ProductDetail;
import com.jeddi.shoppinglistrest.model.ShoppingList;
import com.jeddi.shoppinglistrest.repository.ProductDetailRepository;
import com.jeddi.shoppinglistrest.repository.ShoppingListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
        return slRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));
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
        ShoppingList shoppingList = mpSrvc.mapShoppingListForUpdate(slDto.getTitle(), null);

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

    @Transactional
    public void updateShoppingList(Long shoppingListId, ShoppingListDto slDto) {
        Optional<ShoppingList> sl = slRepo.findById(shoppingListId);
        if (sl.isEmpty()) {
            throw new IllegalStateException("Shopping List with ID : {" + shoppingListId + "} not found");
        }

        if (slDto.getTitle() == null || "".equals(slDto.getTitle())) {
            throw new IllegalStateException("Shopping List Title can't be empty");
        }

        if (slRepo.findByTitle(slDto.getTitle()).isPresent() && !sl.get().getTitle().equals(slDto.getTitle())) {
            throw new IllegalStateException("Shopping List Title is exist, please change the title");
        }

        // Map Shopping List DTO to Model
        ShoppingList shoppingList = mpSrvc.mapShoppingListForUpdate(sl.get(), slDto.getTitle());

        // Save the shopping list first
        slRepo.save(shoppingList);

        // Clear current product detail
//        pdRepo.deleteByShoppinglist_id(sl.get().getId());

        // Map Product Detail DTO to Model
        List<ProductDetail> productDetails = new LinkedList<>();
        if (!slDto.getProductList().isEmpty()) {
            productDetails = mpSrvc.mapProductDetail(shoppingListId, slDto.getProductList());
        }

        // Save the brand-new product detail
        pdRepo.saveAll(productDetails);
    }

    @Transactional
    public void deleteShoppingListById(Long shoppinglist_id) {
        // Delete the child element first
        pdRepo.deleteByShoppinglist_id(shoppinglist_id);

        // Continue with parent deletion
        slRepo.deleteById(shoppinglist_id);
    }
}
