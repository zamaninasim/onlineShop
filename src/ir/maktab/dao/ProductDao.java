package ir.maktab.dao;

import ir.maktab.model.Product;
import ir.maktab.model.enumeration.ProductType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    public Product findProductById(Integer id) throws SQLException {
        String sqlQuery = "select * from products WHERE id = ?";
        PreparedStatement foundedProduct = getConnection().prepareStatement(sqlQuery);
        foundedProduct.setInt(1, id);
        ResultSet resultSet = foundedProduct.executeQuery();
        while (resultSet.next()) {
            String productType = resultSet.getString("product_type");
            String name = resultSet.getString("name");
            Long price = resultSet.getLong("price");
            Integer count = resultSet.getInt("count");
            Product product = new Product(id, ProductType.getVal(productType), name, price, count);
            return product;
        }
        return null;
    }

    public void updateProductCount(Integer productId, Integer newCount) throws SQLException {
        if (getConnection() != null) {
            String sQlQuary = "UPDATE products SET count = ? WHERE id = ?";
            PreparedStatement updateCount = getConnection().prepareStatement(sQlQuary);
            updateCount.setInt(1, newCount);
            updateCount.setInt(2, productId);
            updateCount.executeUpdate();
        }
    }
}
