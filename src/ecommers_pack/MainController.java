package ecommers_pack;




import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class MainController {
    private static CustomerService customerService = new CustomerService();
    private static OrderService orderService = new OrderService();
    private static ProductService productService = new ProductService();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Add Customer");
            System.out.println("2. Get Customer");
            System.out.println("3. List Customers");
            System.out.println("4. Update Customer");
            System.out.println("5. Delete Customer");
            System.out.println("6. Add Product");
            System.out.println("7. Get Product");
            System.out.println("8. List Products");
            System.out.println("9. Update Product");
            System.out.println("10. Delete Product");
            System.out.println("11. Add Order");
            System.out.println("12. Get Order");
            System.out.println("13. List Orders");
            System.out.println("14. Update Order");
            System.out.println("15. Delete Order");
            System.out.println("0. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addCustomer(scanner);
                    break;
                case 2:
                    getCustomer(scanner);
                    break;
                case 3:
                    listCustomers();
                    break;
                case 4:
                    updateCustomer(scanner);
                    break;
                case 5:
                    deleteCustomer(scanner);
                    break;
                case 6:
                    addProduct(scanner);
                    break;
                case 7:
                    getProduct(scanner);
                    break;
                case 8:
                    listProducts();
                    break;
                case 9:
                    updateProduct(scanner);
                    break;
                case 10:
                    deleteProduct(scanner);
                    break;
                case 11:
                    addOrder(scanner);
                    break;
                case 12:
                    getOrder(scanner);
                    break;
                case 13:
                    listOrders();
                    break;
                case 14:
                    updateOrder(scanner);
                    break;
                case 15:
                    deleteOrder(scanner);
                    break;
                case 0:
                    System.out.println("Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void addCustomer(Scanner scanner) {
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();
        System.out.print("Enter customer email: ");
        String email = scanner.nextLine();

        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);

        customerService.createCustomer(customer);
        System.out.println("Customer added successfully.");
    }

    private static void getCustomer(Scanner scanner) {
        System.out.print("Enter customer ID: ");
        int id = scanner.nextInt();

        Customer customer = customerService.getCustomer(id);
        if (customer != null) {
            System.out.println("Customer ID: " + customer.getId());
            System.out.println("Customer Name: " + customer.getName());
            System.out.println("Customer Email: " + customer.getEmail());
        } else {
            System.out.println("Customer not found.");
        }
    }

    private static void listCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        for (Customer customer : customers) {
            System.out.println("Customer ID: " + customer.getId());
            System.out.println("Customer Name: " + customer.getName());
            System.out.println("Customer Email: " + customer.getEmail());
        }
    }

    private static void updateCustomer(Scanner scanner) {
        System.out.print("Enter customer ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); 

        Customer customer = customerService.getCustomer(id);
        if (customer != null) {
            System.out.print("Enter new customer name: ");
            String name = scanner.nextLine();
            System.out.print("Enter new customer email: ");
            String email = scanner.nextLine();

            customer.setName(name);
            customer.setEmail(email);

            customerService.updateCustomer(customer);
            System.out.println("Customer updated successfully.");
        } else {
            System.out.println("Customer not found.");
        }
    }

    private static void deleteCustomer(Scanner scanner) {
        System.out.print("Enter customer ID: ");
        int id = scanner.nextInt();

        customerService.deleteCustomer(id);
        System.out.println("Customer deleted successfully.");
    }

    private static void addProduct(Scanner scanner) {
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();
        System.out.print("Enter product price: ");
        double price = scanner.nextDouble();

        Product product = new Product();
        product.setName(name);
        product.setPrice(price);

        productService.createProduct(product);
        System.out.println("Product added successfully.");
    }

    private static void getProduct(Scanner scanner) {
        System.out.print("Enter product ID: ");
        int id = scanner.nextInt();

        Product product = productService.getProduct(id);
        if (product != null) {
            System.out.println("Product ID: " + product.getId());
            System.out.println("Product Name: " + product.getName());
            System.out.println("Product Price: " + product.getPrice());
        } else {
            System.out.println("Product not found.");
        }
    }

    private static void listProducts() {
        List<Product> products = productService.getAllProducts();
        for (Product product : products) {
            System.out.println("Product ID: " + product.getId());
            System.out.println("Product Name: " + product.getName());
            System.out.println("Product Price: " + product.getPrice());
        }
    }

    private static void updateProduct(Scanner scanner) {
        System.out.print("Enter product ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); 

        Product product = productService.getProduct(id);
        if (product != null) {
            System.out.print("Enter new product name: ");
            String name = scanner.nextLine();
            System.out.print("Enter new product price: ");
            double price = scanner.nextDouble();

            product.setName(name);
            product.setPrice(price);

            productService.updateProduct(product);
            System.out.println("Product updated successfully.");
        } else {
            System.out.println("Product not found.");
        }
    }

    private static void deleteProduct(Scanner scanner) {
        System.out.print("Enter product ID: ");
        int id = scanner.nextInt();

        productService.deleteProduct(id);
        System.out.println("Product deleted successfully.");
    }

    private static void addOrder(Scanner scanner) {
        System.out.print("Enter customer ID: ");
        int customerId = scanner.nextInt();
        scanner.nextLine(); 

        Order order = new Order();
        order.setCustomerId(customerId);
        order.setOrderDate(new Date());

        List<OrderItem> items = new ArrayList<>();
        while (true) {
            System.out.print("Enter product ID (0 to finish): ");
            int productId = scanner.nextInt();
            if (productId == 0) {
                break;
            }

            System.out.print("Enter quantity: ");
            int quantity = scanner.nextInt();

            OrderItem item = new OrderItem();
            item.setProductId(productId);
            item.setQuantity(quantity);
            items.add(item);
        }

        order.setItems(items);
        orderService.createOrder(order);
        System.out.println("Order added successfully.");
    }

    private static void getOrder(Scanner scanner) {
        System.out.print("Enter order ID: ");
        int id = scanner.nextInt();

        Order order = orderService.getOrder(id);
        if (order != null) {
            System.out.println("Order ID: " + order.getId());
            System.out.println("Customer ID: " + order.getCustomerId());
            System.out.println("Order Date: " + order.getOrderDate());

            for (OrderItem item : order.getItems()) {
                System.out.println("Product ID: " + item.getProductId());
                System.out.println("Quantity: " + item.getQuantity());
            }
        } else {
            System.out.println("Order not found.");
        }
    }

    private static void listOrders() {
        List<Order> orders = orderService.getAllOrders();
        for (Order order : orders) {
            System.out.println("Order ID: " + order.getId());
            System.out.println("Customer ID: " + order.getCustomerId());
            System.out.println("Order Date: " + order.getOrderDate());

            for (OrderItem item : order.getItems()) {
                System.out.println("Product ID: " + item.getProductId());
                System.out.println("Quantity: " + item.getQuantity());
            }
        }
    }

    private static void updateOrder(Scanner scanner) {
        System.out.print("Enter order ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); 

        Order order = orderService.getOrder(id);
        if (order != null) {
            System.out.print("Enter new customer ID: ");
            int customerId = scanner.nextInt();
            scanner.nextLine(); 
            order.setCustomerId(customerId);
            order.setOrderDate(new Date());

            List<OrderItem> items = new ArrayList<>();
            while (true) {
                System.out.print("Enter product ID (0 to finish): ");
                int productId = scanner.nextInt();
                if (productId == 0) {
                    break;
                }

                System.out.print("Enter quantity: ");
                int quantity = scanner.nextInt();

                OrderItem item = new OrderItem();
                item.setProductId(productId);
                item.setQuantity(quantity);
                items.add(item);
            }

            order.setItems(items);
            orderService.updateOrder(order);
            System.out.println("Order updated successfully.");
        } else {
            System.out.println("Order not found.");
        }
    }

    private static void deleteOrder(Scanner scanner) {
        System.out.print("Enter order ID: ");
        int id = scanner.nextInt();

        orderService.deleteOrder(id);
        System.out.println("Order deleted successfully.");
    }
}
