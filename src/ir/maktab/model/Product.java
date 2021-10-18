package ir.maktab.model;

import ir.maktab.model.enumeration.ProductType;
import lombok.Data;

@Data
public class Product {
    private Integer id;
    private ProductType productType;
    private String name;
    private Long price;
    private Integer count;
}
