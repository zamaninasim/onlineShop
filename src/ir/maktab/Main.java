package ir.maktab;

import ir.maktab.dao.ProductDao;
import ir.maktab.dao.UserDao;
import ir.maktab.model.Manager;
import ir.maktab.model.Product;
import ir.maktab.model.User;
import ir.maktab.model.enumeration.Gender;
import ir.maktab.model.enumeration.ProductType;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    private static final Scanner input = new Scanner(System.in);
    static Manager manager;
    static ProductDao productDao;
    static UserDao userDao;

    public Main() throws SQLException, ClassNotFoundException {
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        productDao = new ProductDao();
        manager = new Manager();
        userDao = new UserDao();
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
                System.out.println("1)sign in 2)sign up");
                Integer choice1 = input.nextInt();
                switch (choice1) {
                    case 1 :
                        System.out.println("enter your phone number:");
                        String username = input.next();
                        //if (phoneNumber.equals())
                    case 2:
                        System.out.println("enter your info :fullName,phoneNumber,email,gender,birthDate,nationalId");
                        String information = input.next();
                        addUser(information);
                }
            default:
                System.out.println("");

        }

    }

    private static void addUser(String information) throws SQLException {
        String[] arrOfInfo = information.split(",", 6);
        String fullName = arrOfInfo[0];
        String phoneNumber = arrOfInfo[1];
        String email =arrOfInfo[2];
        Gender gender = Gender.getVal(arrOfInfo[3].toUpperCase());
        Date birthDate=Date.valueOf(arrOfInfo[4]);
        String nationalId = arrOfInfo[5];
        User user = new User(fullName,phoneNumber,email,gender,birthDate,nationalId);
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
