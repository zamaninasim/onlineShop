package ir.maktab;

import ir.maktab.dao.OrderDao;
import ir.maktab.dao.ProductDao;
import ir.maktab.dao.UserDao;
import ir.maktab.model.Manager;
import ir.maktab.model.Order;
import ir.maktab.model.Product;
import ir.maktab.model.User;
import ir.maktab.model.enumeration.Gender;
import ir.maktab.model.enumeration.OrderStatus;
import ir.maktab.model.enumeration.ProductType;
import ir.maktab.service.ProductService;
import ir.maktab.service.UserService;

import java.sql.Date;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {
    private static final Scanner input = new Scanner(System.in);
    static Manager manager;
    static ProductDao productDao;
    static UserDao userDao;
    static OrderDao orderDao;
    static ProductService productService;
    static UserService userService;

    public Main() throws SQLException, ClassNotFoundException {
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        productDao = new ProductDao();
        manager = new Manager();
        userDao = new UserDao();
        orderDao = new OrderDao();
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
                    userActions(username);
                } else {
                    System.out.println("enter your info :fullName,email,gender,birthDate,nationalId");
                    String information = input.next();
                    addUser(information, username);
                    userActions(username);
                }
                break;
        }

    }

    private static void userActions(String phoneNumber) throws SQLException, ClassNotFoundException {
        System.out.println("product list");
        System.out.println(productService.showAllProduct());
        System.out.println("1)add product to your cart \n2)delete product from your cart " +
                "\n3)View Cart Products \n4)view Cart item prices \n5)Final purchase confirmation");
        Integer choice = input.nextInt();
        switch (choice) {
            case 1:
                System.out.println("Enter the product ID to add to your cart:");
                Integer productId = input.nextInt();
                System.out.println("Enter the number you want:");
                Integer count = input.nextInt();
                User user = userDao.findUserByPhoneNumber(phoneNumber);
                Integer userId=user.getId();
                Product product =productDao.findProductById(productId);
                Set<Order> orders = orderDao.findRezervedOrderOfUser(userId);
                int numberOfOrders = orders.size();
                if(count<= product.getCount() && numberOfOrders<6){
                    OrderStatus orderStatus = OrderStatus.RESERVED;
                    Order order = new Order(user,product,count,orderStatus);
                    orderDao.save(order);
                }else {
                    System.out.println("You can not select this product.");
                }



        }

    }

    private static void addUser(String information, String phoneNumber) throws SQLException {
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
