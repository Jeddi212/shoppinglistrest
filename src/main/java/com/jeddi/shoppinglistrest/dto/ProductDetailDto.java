package com.jeddi.shoppinglistrest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailDto {
    String productName;
    int quantity;
    String memo;
}
