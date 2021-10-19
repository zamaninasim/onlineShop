package ir.maktab.service;

import ir.maktab.dao.Dao;
import ir.maktab.dao.ProductDao;
import ir.maktab.model.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductService {
    private final ProductDao productDao;

    public ProductService() throws SQLException, ClassNotFoundException {
        productDao = new ProductDao();
    }

    public List<Product> showAllProduct() throws SQLException {
        List<Product> products =productDao.findAllProduct();
        for (Product product : products) {
            product.toString();
        }
        return products;
    }

}
