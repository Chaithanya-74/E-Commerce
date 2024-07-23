package ecommers_pack;



import java.util.List;

import ecommers.DAO.OrderDAO;

public class OrderService {
    private OrderDAO orderDAO = new OrderDAO();

    public void createOrder(Order order) {
        orderDAO.createOrder(order);
    }

    public Order getOrder(int id) {
        return orderDAO.getOrder(id);
    }

    public List<Order> getAllOrders() {
        return orderDAO.getAllOrders();
    }

    public void updateOrder(Order order) {
        orderDAO.updateOrder(order);
    }

    public void deleteOrder(int id) {
        orderDAO.deleteOrder(id);
    }
}
