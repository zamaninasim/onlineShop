package ir.maktab.dao;

import ir.maktab.model.Order;

import java.sql.SQLException;
import java.sql.Statement;

public class OrderDao extends Dao {
    public OrderDao() throws ClassNotFoundException, SQLException {
    }

    public String save(Order order) throws SQLException {
        if (getConnection() != null) {
            Statement statement = getConnection().createStatement();
            String sqlQuary = String.format("INSERT INTO orders (user_id_fk,product_id_fk,count,order_status)" +
                            "VALUES ('%d','%d','%d','%s')", order.getUser().getId(), order.getProduct().getId(), order.getCount()
                    , order.getOrderStatus());
            int i = statement.executeUpdate(sqlQuary);
            if (i == 1) {
                return "order Information was recorded";
            }
        }
        return null;
    }
}
