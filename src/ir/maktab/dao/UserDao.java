package ir.maktab.dao;

import ir.maktab.model.User;
import ir.maktab.model.enumeration.Gender;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao extends Dao {
    public UserDao() throws ClassNotFoundException, SQLException {
    }

    public String save(User user) throws SQLException {
        if (getConnection() != null) {
            Statement statement = getConnection().createStatement();
            String sqlQuary = String.format("INSERT INTO users (full_name,phone_number,email,gender,birth_date,national_id)" +
                            "VALUES ('%s','%s','%s','%s','%tF','%s')", user.getFullName(), user.getPhoneNumber(), user.getEmail(),
                    user.getGender(), user.getBirthDate(), user.getNationalId());
            int i = statement.executeUpdate(sqlQuary);
            if (i == 1) {
                return "user Information was recorded";
            }
        }
        return null;
    }

    public User findUserByPhoneNumber(String phoneNumber) throws SQLException {
        String sqlQuery = "select * from users WHERE phone_number = ?";
        PreparedStatement foundedUser = getConnection().prepareStatement(sqlQuery);
        foundedUser.setString(1, phoneNumber);
        ResultSet resultSet = foundedUser.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String fullName = resultSet.getString("full_name");
            String email = resultSet.getString("email");
            String gender = resultSet.getString("gender");
            Date birthDate = resultSet.getDate("birth_date");
            String nationalId = resultSet.getString("national_id");
            User user = new User(id, fullName, phoneNumber, email, Gender.getVal(gender), birthDate, nationalId);
            return user;
        }
        return null;
    }

    public User findUserById(Integer id) throws SQLException {
        String sqlQuery = "select * from users WHERE id = ?";
        PreparedStatement foundedUser = getConnection().prepareStatement(sqlQuery);
        foundedUser.setInt(1, id);
        ResultSet resultSet = foundedUser.executeQuery();
        while (resultSet.next()) {
            String fullName = resultSet.getString("full_name");
            String phoneNumber = resultSet.getString("phone_number");
            String email = resultSet.getString("email");
            String gender = resultSet.getString("gender");
            Date birthDate = resultSet.getDate("birth_date");
            String nationalId = resultSet.getString("national_id");
            User user = new User(id, fullName, phoneNumber, email, Gender.getVal(gender), birthDate, nationalId);
            return user;
        }
        return null;
    }
}
