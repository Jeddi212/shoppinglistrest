package com.jeddi.shoppinglistrest.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetail {
    @EmbeddedId
    private ProductDetailId id;

    @Column
    private String productName;

    @Column
    private int quantity;

    @Column
    private String memo;

    public void setId(long shoppinglist_id, int no) {
        id = new ProductDetailId();
        id.setShoppinglist_id(shoppinglist_id);
        id.setNo(no);
    }
}
