package ir.maktab.dao;

import ir.maktab.model.Product;

import java.sql.SQLException;
import java.sql.Statement;

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
}
