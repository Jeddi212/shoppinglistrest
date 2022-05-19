package com.jeddi.shoppinglistrest.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingList {
    @Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(
            name = "shoppinglists_sequence",
            sequenceName = "shoppinglist_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "shoppinglists_sequence"
    )
    private Long id;

    @Column(columnDefinition = "TIMESTAMP") // Defining column type
    private LocalDateTime dateTime;

    @Column
    private String title;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL/*, orphanRemoval = true*/)
    @JoinColumn(name = "shoppinglist_id") // Pick the foreign key column
    private List<ProductDetail> productList;
}
