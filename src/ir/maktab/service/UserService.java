package ir.maktab.service;

import ir.maktab.dao.Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService extends Dao {
    public UserService() throws ClassNotFoundException, SQLException {
    }

    public boolean isUserExist(String phoneNumber) throws SQLException {
        String sqlQuery = "SELECT * FROM users  WHERE phone_number = ?";
        PreparedStatement findUser = getConnection().prepareStatement(sqlQuery);
        findUser.setString(1, phoneNumber);
        ResultSet resultSet = findUser.executeQuery();
        if (!resultSet.next()) {
            return false;
        }
        return true;
    }
}
