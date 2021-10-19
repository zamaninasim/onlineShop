package ir.maktab.dao;

import ir.maktab.model.Order;
import ir.maktab.model.Product;
import ir.maktab.model.User;
import ir.maktab.model.enumeration.OrderStatus;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    public List<Order> findRezervedOrderOfUser(Integer userId) throws SQLException, ClassNotFoundException {
        String sqlQuery = "select * from orders WHERE user_id_fk = ? AND order_status = 'RESERVED'";
        PreparedStatement foundedProduct = getConnection().prepareStatement(sqlQuery);
        foundedProduct.setInt(1, userId);
        ResultSet resultSet = foundedProduct.executeQuery();
        List<Order> orders = new ArrayList<>();
        while (resultSet.next()) {
            Integer id = resultSet.getInt("id");
            Integer productId = resultSet.getInt("product_id_fk");
            Integer count = resultSet.getInt("count");
            String orderStatus = resultSet.getString("order_status");
            userDao = new UserDao();
            User user = userDao.findUserById(userId);
            productDao = new ProductDao();
            Product product = productDao.findProductById(productId);
            Order order = new Order(id, user, product, count, OrderStatus.getVal(orderStatus));
            orders.add(order);
        }
        return orders;
    }

    public boolean isUserHaveOrdere(Integer userId) throws SQLException {
        String sqlQuery = "SELECT user_id_fk FROM orders  WHERE user_id_fk = ?";
        PreparedStatement findID = getConnection().prepareStatement(sqlQuery);
        findID.setInt(1, userId);
        ResultSet resultSet = findID.executeQuery();
        if (!resultSet.next()) {
            return false;
        }
        return true;
    }

    public boolean isThisUserOrderedThisProduct(Integer userId , Integer productId) throws SQLException {
        String sqlQuery = "SELECT user_id_fk FROM orders  WHERE user_id_fk = ? AND product_id_fk=?";
        PreparedStatement findID = getConnection().prepareStatement(sqlQuery);
        findID.setInt(1, userId);
        findID.setInt(2, productId);
        ResultSet resultSet = findID.executeQuery();
        if (!resultSet.next()) {
            return false;
        }
        return true;
    }

    public void updateOrderCount(Integer userId,Integer productId, Integer newCount) throws SQLException {
        if (getConnection() != null) {
            String sQlQuary = "UPDATE orders SET count = ? WHERE user_id_fk = ? AND  product_id_fk= ? AND order_status = 'RESERVED'";
            PreparedStatement updateCount = getConnection().prepareStatement(sQlQuary);
            updateCount.setInt(1, newCount);
            updateCount.setInt(2, userId);
            updateCount.setInt(3, productId);
            updateCount.executeUpdate();
        }
    }

    public Integer ordereCount(Integer userId , Integer productId) throws SQLException {
        String sqlQuery = "SELECT count FROM orders  WHERE user_id_fk = ? AND product_id_fk=?";
        PreparedStatement findID = getConnection().prepareStatement(sqlQuery);
        findID.setInt(1, userId);
        findID.setInt(2, productId);
        ResultSet resultSet = findID.executeQuery();
        while (resultSet.next()) {
            Integer count = resultSet.getInt("count");
            return count;
        }
        return 0;
    }

    public void deleteOrderOfUser(Integer userId,Integer productId) throws SQLException {
        if (getConnection() != null) {
            String sQlQuary = "DELETE  FROM orders WHERE user_id_fk = ? AND  product_id_fk= ? AND order_status = 'RESERVED'";
            PreparedStatement deleteOrder = getConnection().prepareStatement(sQlQuary);
            deleteOrder.setInt(1, userId);
            deleteOrder.setInt(2, productId);
            deleteOrder.executeUpdate();
        }
    }

    public List<Long> calculateFinalPriceOfUserOrders(Integer userId) throws SQLException, ClassNotFoundException {
        String sqlQuery = "select * from orders WHERE user_id_fk = ? AND order_status = 'RESERVED'";
        PreparedStatement foundedProduct = getConnection().prepareStatement(sqlQuery);
        foundedProduct.setInt(1, userId);
        ResultSet resultSet = foundedProduct.executeQuery();
        List<Long> prices = new ArrayList<>();
        while (resultSet.next()) {
            Integer productId = resultSet.getInt("product_id_fk");
            Integer count = resultSet.getInt("count");
            productDao = new ProductDao();
            Product product = productDao.findProductById(productId);
            Long price = product.getPrice()*count;
            prices.add(price);
        }
        return prices;
    }
}

