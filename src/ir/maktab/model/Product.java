package ir.maktab.model;

import ir.maktab.model.enumeration.ProductType;
import lombok.Data;

@Data
public class Product {
    private Integer id;
    private String name;
    private ProductType productType;
    private Long price;
    private Integer count;
}
