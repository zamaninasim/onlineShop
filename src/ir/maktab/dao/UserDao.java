package ir.maktab.dao;

import ir.maktab.model.User;

import java.sql.SQLException;
import java.sql.Statement;

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
}
