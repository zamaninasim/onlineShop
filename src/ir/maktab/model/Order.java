package ir.maktab.model;

import ir.maktab.model.enumeration.OrderStatus;
import lombok.Data;

@Data
public class Order {
    private User user;
    private Product product;
    private Integer count;
    private OrderStatus orderStatus;
}
