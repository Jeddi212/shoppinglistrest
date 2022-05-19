package com.jeddi.shoppinglistrest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailId implements Serializable {
    @Serial
    private static final long serialVersionUID = 896086643749897821L;
    private long shoppinglist_id;
    private int no;
}
