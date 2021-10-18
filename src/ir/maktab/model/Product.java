package ir.maktab.model;

import ir.maktab.model.enumeration.ProductType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Product {
    private Integer id;
    private ProductType productType;
    private String name;
    private Long price;
    private Integer count;

    public Product(ProductType productType, String name, Long price, Integer count) {
        this.productType = productType;
        this.name = name;
        this.price = price;
        this.count = count;
    }
}
