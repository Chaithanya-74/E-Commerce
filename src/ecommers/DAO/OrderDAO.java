package ecommers.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import ecommers_pack.Order;
import ecommers_pack.OrderItem;
import ecommers_pack.database_connection.DBConnection;

public class OrderDAO {
    public void createOrder(Order order) {
        String orderSql = "INSERT INTO orders (customer_id, order_date) VALUES (?, ?)";
        String orderItemSql = "INSERT INTO order_items (order_id, product_id, quantity) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement orderPstmt = conn.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement orderItemPstmt = conn.prepareStatement(orderItemSql)) {
            conn.setAutoCommit(false);

            orderPstmt.setInt(1, order.getCustomerId());
            orderPstmt.setDate(2, new java.sql.Date(order.getOrderDate().getTime()));
            orderPstmt.executeUpdate();

            ResultSet rs = orderPstmt.getGeneratedKeys();
            if (rs.next()) {
                int orderId = rs.getInt(1);
                for (OrderItem item : order.getItems()) {
                    orderItemPstmt.setInt(1, orderId);
                    orderItemPstmt.setInt(2, item.getProductId());
                    orderItemPstmt.setInt(3, item.getQuantity());
                    orderItemPstmt.addBatch();
                }
                orderItemPstmt.executeBatch();
            }

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Order getOrder(int id) {
        String orderSql = "SELECT * FROM orders WHERE id = ?";
        String orderItemSql = "SELECT * FROM order_items WHERE order_id = ?";
        Order order = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement orderPstmt = conn.prepareStatement(orderSql);
             PreparedStatement orderItemPstmt = conn.prepareStatement(orderItemSql)) {

            orderPstmt.setInt(1, id);
            ResultSet orderRs = orderPstmt.executeQuery();

            if (orderRs.next()) {
                order = new Order();
                order.setId(orderRs.getInt("id"));
                order.setCustomerId(orderRs.getInt("customer_id"));
                order.setOrderDate(orderRs.getDate("order_date"));

                List<OrderItem> items = new ArrayList<>();
                orderItemPstmt.setInt(1, order.getId());
                ResultSet orderItemRs = orderItemPstmt.executeQuery();
                while (orderItemRs.next()) {
                    OrderItem item = new OrderItem();
                    item.setOrderId(orderItemRs.getInt("order_id"));
                    item.setProductId(orderItemRs.getInt("product_id"));
                    item.setQuantity(orderItemRs.getInt("quantity"));
                    items.add(item);
                }
                order.setItems(items);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    public List<Order> getAllOrders() {
        String orderSql = "SELECT * FROM orders";
        String orderItemSql = "SELECT * FROM order_items WHERE order_id = ?";
        List<Order> orders = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             Statement orderStmt = conn.createStatement();
             PreparedStatement orderItemPstmt = conn.prepareStatement(orderItemSql);
             ResultSet orderRs = orderStmt.executeQuery(orderSql)) {

            while (orderRs.next()) {
                Order order = new Order();
                order.setId(orderRs.getInt("id"));
                order.setCustomerId(orderRs.getInt("customer_id"));
                order.setOrderDate(orderRs.getDate("order_date"));

                List<OrderItem> items = new ArrayList<>();
                orderItemPstmt.setInt(1, order.getId());
                ResultSet orderItemRs = orderItemPstmt.executeQuery();
                while (orderItemRs.next()) {
                    OrderItem item = new OrderItem();
                    item.setOrderId(orderItemRs.getInt("order_id"));
                    item.setProductId(orderItemRs.getInt("product_id"));
                    item.setQuantity(orderItemRs.getInt("quantity"));
                    items.add(item);
                }
                order.setItems(items);
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public void updateOrder(Order order) {
        String orderSql = "UPDATE orders SET customer_id = ?, order_date = ? WHERE id = ?";
        String orderItemSql = "INSERT INTO order_items (order_id, product_id, quantity) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE quantity = VALUES(quantity)";
        String deleteOrderItemSql = "DELETE FROM order_items WHERE order_id = ? AND product_id NOT IN (?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement orderPstmt = conn.prepareStatement(orderSql);
             PreparedStatement orderItemPstmt = conn.prepareStatement(orderItemSql);
             PreparedStatement deleteOrderItemPstmt = conn.prepareStatement(deleteOrderItemSql)) {

            conn.setAutoCommit(false);

            orderPstmt.setInt(1, order.getCustomerId());
            orderPstmt.setDate(2, new java.sql.Date(order.getOrderDate().getTime()));
            orderPstmt.setInt(3, order.getId());
            orderPstmt.executeUpdate();

            for (OrderItem item : order.getItems()) {
                orderItemPstmt.setInt(1, order.getId());
                orderItemPstmt.setInt(2, item.getProductId());
                orderItemPstmt.setInt(3, item.getQuantity());
                orderItemPstmt.addBatch();
            }
            orderItemPstmt.executeBatch();

            // Build comma-separated list of product IDs for deletion query
            StringBuilder productIds = new StringBuilder();
            for (OrderItem item : order.getItems()) {
                productIds.append(item.getProductId()).append(",");
            }
            deleteOrderItemPstmt.setInt(1, order.getId());
            deleteOrderItemPstmt.setString(2, productIds.toString().replaceAll(",$", ""));
            deleteOrderItemPstmt.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteOrder(int id) {
        String orderSql = "DELETE FROM orders WHERE id = ?";
        String orderItemSql = "DELETE FROM order_items WHERE order_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement orderPstmt = conn.prepareStatement(orderSql);
             PreparedStatement orderItemPstmt = conn.prepareStatement(orderItemSql)) {

            conn.setAutoCommit(false);

            orderPstmt.setInt(1, id);
            orderPstmt.executeUpdate();

            orderItemPstmt.setInt(1, id);
            orderItemPstmt.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
