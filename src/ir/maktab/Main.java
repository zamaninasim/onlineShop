package ir.maktab;

import ir.maktab.dao.ProductDao;
import ir.maktab.dao.UserDao;
import ir.maktab.model.Manager;
import ir.maktab.model.Product;
import ir.maktab.model.User;
import ir.maktab.model.enumeration.Gender;
import ir.maktab.model.enumeration.ProductType;
import ir.maktab.service.ProductService;
import ir.maktab.service.UserService;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    private static final Scanner input = new Scanner(System.in);
    static Manager manager;
    static ProductDao productDao;
    static UserDao userDao;
    static ProductService productService;
    static UserService userService;

    public Main() throws SQLException, ClassNotFoundException {
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        productDao = new ProductDao();
        manager = new Manager();
        userDao = new UserDao();
        userService = new UserService();
        productService = new ProductService();
        System.out.println("Choose your role : 1)manager 2)user");
        Integer choice = input.nextInt();
        switch (choice) {
            case (1):
                boolean repeat = false;
                do {
                    System.out.println("enter username:");
                    String username = input.next();
                    System.out.println("enter password:");
                    String password = input.next();
                    if (username.equals(manager.getUsername()) && password.equals(manager.getPassword())) {
                        repeat = true;
                        System.out.println("welcome");
                        System.out.println("enter product info : product type,product name,price,count");
                        String information = input.next();
                        addProduct(information);
                    } else {
                        System.out.println("username or password is wrong! TRY AGAIN ");
                    }
                } while (!repeat);
                break;
            case 2:
                System.out.println("enter your phone number:");
                String username = input.next();
                Boolean usernameExist = userService.isUserExist(username);
                if (usernameExist) {
                    System.out.println("you have an account");
                } else {
                    System.out.println("enter your info :fullName,email,gender,birthDate,nationalId");
                    String information = input.next();
                    addUser(information,username);
                }
        }

    }

    private static void addUser(String information,String phoneNumber) throws SQLException {
        String[] arrOfInfo = information.split(",", 5);
        String fullName = arrOfInfo[0];
        String email = arrOfInfo[1];
        Gender gender = Gender.getVal(arrOfInfo[2].toUpperCase());
        Date birthDate = Date.valueOf(arrOfInfo[3]);
        String nationalId = arrOfInfo[4];
        User user = new User(fullName, phoneNumber, email, gender, birthDate, nationalId);
        System.out.println(userDao.save(user));
    }

    private static void addProduct(String information) throws SQLException {
        String[] arrOfInfo = information.split(",", 4);
        ProductType productType = ProductType.getVal(arrOfInfo[0].toUpperCase());
        String productName = arrOfInfo[1];
        Long price = Long.parseLong(arrOfInfo[2]);
        Integer count = Integer.parseInt(arrOfInfo[3]);
        Product product = new Product(productType, productName, price, count);
        System.out.println(productDao.save(product));
    }
}
