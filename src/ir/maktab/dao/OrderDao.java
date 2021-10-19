package ir.maktab.dao;

import ir.maktab.model.Order;
import ir.maktab.model.Product;
import ir.maktab.model.User;
import ir.maktab.model.enumeration.OrderStatus;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class OrderDao extends Dao {
    static UserDao userDao;
    static ProductDao productDao;
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

    public Set<Order> findRezervedOrderOfUser(Integer userId) throws SQLException, ClassNotFoundException {
        String sqlQuery = "select * from orders WHERE user_id_fk = ? AND order_status = RESERVED";
        PreparedStatement foundedProduct = getConnection().prepareStatement(sqlQuery);
        foundedProduct.setInt(1, userId);
        ResultSet resultSet = foundedProduct.executeQuery();
        Set<Order> orders = new HashSet<>();
        while (resultSet.next()) {
            Integer id = resultSet.getInt("id");
            Integer productId = resultSet.getInt("product_id_fk");
            Integer count = resultSet.getInt("count");
            String orderStatus = resultSet.getString("order_status");
            userDao = new UserDao();
            User user = userDao.findUserById(userId);
            productDao = new ProductDao();
            Product product = productDao.findProductById(productId);
            Order order = new Order(id,user,product,count, OrderStatus.getVal(orderStatus));
            orders.add(order);
        }
        return orders;
    }
}
