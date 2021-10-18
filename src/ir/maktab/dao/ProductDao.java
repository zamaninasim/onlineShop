package ir.maktab.dao;

import ir.maktab.model.Product;
import ir.maktab.model.enumeration.ProductType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDao extends Dao {

    public ProductDao() throws ClassNotFoundException, SQLException {
    }

    public String save(Product product) throws SQLException {
        if (getConnection() != null) {
            Statement statement = getConnection().createStatement();
            String sqlQuary = String.format("INSERT INTO products (product_type,name,price,count)" +
                            "VALUES ('%s','%s','%d','%d')", product.getProductType(), product.getName(),
                    product.getPrice(), product.getCount());
            int i = statement.executeUpdate(sqlQuary);
            if (i == 1) {
                return "product Information was recorded";
            }
        }
        return null;
    }

    public List<Product> findAllProduct() throws SQLException {
        PreparedStatement statement = getConnection().prepareStatement("select * from products");
        ResultSet resultSet = statement.executeQuery();
        List<Product> products = new ArrayList<>();
        while (resultSet.next()) {
            Integer id = resultSet.getInt("id");
            String productType = resultSet.getString("product_type");
            String productName = resultSet.getString("name");
            Long price = resultSet.getLong("price");
            Integer count = resultSet.getInt("count");
            Product product = new Product(id, ProductType.getVal(productType), productName, price, count);
            products.add(product);
        }
        return products;
    }
}
