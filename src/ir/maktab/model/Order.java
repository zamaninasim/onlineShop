package ir.maktab.model;

import ir.maktab.model.enumeration.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class Order {
    private Integer id;
    private User user;
    private Product product;
    private Integer count;
    private OrderStatus orderStatus;

    public Order(User user, Product product, Integer count, OrderStatus orderStatus) {
        this.user = user;
        this.product = product;
        this.count = count;
        this.orderStatus = orderStatus;
    }

    @Override
    public String toString() {
        return "{" +
                "order Id=" + id +
                ", user Id=" + user.getId() +
                ", product Id=" + product.getId() +
                ", count=" + count +
                ", orderStatus=" + orderStatus +
                "}\n";
    }
}
