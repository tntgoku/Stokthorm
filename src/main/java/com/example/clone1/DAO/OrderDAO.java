package com.example.clone1.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.example.clone1.Model.*;

@Repository
public class OrderDAO {
    /*
     * Triển khai OrderDAO ở đây
     * Lớp này sẽ chứa các phương thức để tương tác với cơ sở dữ liệu cho các
     * hoạt động liên quan đến đơn hàng
     * Bạn cần triển khai các phương thức để tạo, đọc, cập nhật và xóa đơn hàng
     * Bạn cần sử dụng JdbcTemplate hoặc bất kỳ phương pháp nào khác để thực hiện
     * các truy vấn SQL
     */
    @Autowired
    private JdbcTemplate template;
    @Autowired
    private ProductDAO productDAO;

    // Ví dụ về phương thức để tạo đơn hàng
    public int createOrder(Order order) {
        /*
         * Triển khai logic của bạn để tạo đơn hàng trong cơ sở dữ liệu
         * You can use JdbcTemplate or any other method to execute SQL queries
         * Bạn có thể sử dụng JdbcTemplate hoặc bất kỳ phương pháp nào khác để thực thi
         * các truy vấn SQL
         * Ví dụ: sử dụng JdbcTemplate để thực hiện truy vấn SQL chèn đơn hàng vào cơ sở
         * dữ liệu
         */
        // Giả sử bạn có một bảng đơn hàng trong cơ sở dữ liệu với các cột tương ứng

        // String sql = "INSERT INTO Orders ( UserID, Status, TotalAmount) VALUES (?, ?,
        // ?)";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String timestampString = sdf.format(new Date()); // Lấy thời gian hiện tại dưới dạng String
        order.setOrderDate(timestampString); // Đặt thời gian hiện tại vào đơn hàng
        String sql = "INSERT INTO Orders (OrderID, UserID, OrderDate, Note, ShippingFee, TotalAmount, paymentMethod, paymentStatus) VALUES  (?, ?, ?, ?, ?, ?, ?, ?)";
        int id = Integer.parseInt(getLatestOrderId()); // Lấy ID đơn hàng mới nhất
        id++;
        order.setOrderId(String.valueOf(id)); // Đặt ID đơn hàng mới nhất vào đơn hàng
        try {
            int status = template.update(sql,
                    order.getOrderId(), // OrderID
                    order.getUserId(), // UserID
                    order.getOrderDate(), // OrderDate
                    order.getNote(), // Note
                    0, // ShippingFee
                    order.getTotalAmount(), // TotalAmount
                    order.getPaymentMethod(), // paymentMethod
                    order.getPaymentMethod().equals("VNPAY") ? "Completed" : "Pending"); // paymentStatus
            // Thêm logic để chèn các sản phẩm trong giỏ hàng vào bảng OrderDetails nếu

            return status; // Trả về số dòng bị ảnh hưởng (thường là 1 nếu thành công)
        } catch (Exception e) {
            e.printStackTrace(); // In lỗi ra console/log
            return -1; // Trả về -1 để báo lỗi
        }
    }

    // Phương thức để lấy ID đơn hàng mới nhất
    public String getLatestOrderId() {
        String sql = "SELECT TOP 1 OrderID FROM Orders ORDER BY OrderID DESC";
        try {
            return template.queryForObject(sql, String.class);
        } catch (EmptyResultDataAccessException e) {
            // Handle case where no result is found, return null or a default value
            System.out.println("No orders found in the database.");
            return null; // or throw a custom exception
        }
    }

    // Phương thức để thêm đơn hàng vào bảng Payments
    public int addtoPayment(Order order) {
        String sql = "INSERT INTO Payments (OrderID, PaymentMethod, PaymentStatus) \r\n" + //
                "VALUES (?, ?, ?)";
        try {
            int status = template.update(sql,
                    order.getUserId(),
                    order.getPaymentMethod(),
                    order.getPaymentMethod().equals("COD") ? "Chưa thanh toán" : "Đã thanh toán");
            return status; // Trả về số dòng bị ảnh hưởng (thường là 1 nếu thành công)
        } catch (Exception e) {
            e.printStackTrace(); // In lỗi ra console/log
            return -1; // Trả về -1 để báo lỗi
        }
    }

    // Phuơng thức để thêm tất cả các sản phẩm trong giỏ hàng vào bảng OrderDetails
    public int addOrderDetails(String orderId, List<CartItem> cartItems) {
        String sql = "INSERT INTO OrderDetails(OrderID, ProductID, Quantity, Price) VALUES (?, ?, ?, ?)";
        try {
            for (CartItem item : cartItems) {
                int status = template.update(sql, orderId, item.getIDItem(), item.getQuantity(), item.getGiasp());
                if (status == 0) {
                    return -1; // Nếu không thể chèn một sản phẩm, trả về -1
                }
                System.out.println("Thêm sản phẩm vào đơn hàng thành công: " + item.getIDItem());
            }
            return 1; // Trả về số dòng bị ảnh hưởng (thường là 1 nếu thành công)
        } catch (Exception e) {
            e.printStackTrace(); // In lỗi ra console/log
            return -1; // Trả về -1 để báo lỗi
        }
    }

    // Phương thức sửa đơn hàng
    public int updateOrder(Order order) {
        String sql = "UPDATE Orders SET UserID = ?, OrderDate = ?,  Note = ?, PaymentMethod = ?, Status = ?, TotalAmount = ? WHERE OrderID = ?";
        try {
            int status = template.update(sql, order.getUserId(),
                    order.getOrderDate(), order.getNote(),
                    order.getPaymentMethod(), order.getStatus(),
                    order.getTotalAmount(), order.getOrderId());
            return status; // Trả về số dòng bị ảnh hưởng (thường là 1 nếu thành công)
        } catch (Exception e) {
            e.printStackTrace(); // In lỗi ra console/log
            return -1; // Trả về -1 để báo lỗi
        }
    }

    // Phương thức để cập nhật sản phẩm trong hóa đơn hàng
    public int updateOrderDetails(String orderId, List<CartItem> cartItems) {
        String sql = "UPDATE OrderDetails SET Quantity = ?, Price = ? WHERE OrderID = ? AND ProductID = ?";
        try {
            for (CartItem item : cartItems) {
                int status = template.update(sql, item.getQuantity(), item.getGiasp(), orderId, item.getIDItem());
                if (status == 0) {
                    return -1; // Nếu không thể cập nhật một sản phẩm, trả về -1
                }
                System.out.println("Cập nhật sản phẩm trong đơn hàng thành công: " + item.getIDItem());
            }
            return 1; // Trả về số dòng bị ảnh hưởng (thường là 1 nếu thành công)
        } catch (Exception e) {
            e.printStackTrace(); // In lỗi ra console/log
            return -1; // Trả về -1 để báo lỗi
        }
    }

    // Phương thức để xóa đơn hàng
    public int deleteOrder(String orderId) {
        String sql = "DELETE FROM Orders WHERE OrderID = ?";
        try {
            int status = template.update(sql, orderId);
            return status; // Trả về số dòng bị ảnh hưởng (thường là 1 nếu thành công)
        } catch (Exception e) {
            e.printStackTrace(); // In lỗi ra console/log
            return -1; // Trả về -1 để báo lỗi
        }
    }

    // Phương thức để xóa sản phẩm trong hóa đơn hàng
    public int deleteOrderDetails(String orderId, String productId) {
        String sql = "DELETE FROM OrderDetails WHERE OrderID = ? AND ProductID = ?";
        try {
            int status = template.update(sql, orderId, productId);
            return status; // Trả về số dòng bị ảnh hưởng (thường là 1 nếu thành công)
        } catch (Exception e) {
            e.printStackTrace(); // In lỗi ra console/log
            return -1; // Trả về -1 để báo lỗi
        }
    }

    // Phương thức để lấy tất cả các đơn hàng
    public List<Order> getAllOrders() {
        String sql = "SELECT * FROM Orders";
        try {
            return template.query(sql, (rs, rowNum) -> {
                Order order = new Order();
                order.setOrderId(rs.getString("OrderID"));
                order.setUserId(rs.getString("UserID"));
                order.setOrderDate(rs.getString("OrderDate"));
                order.setNote(rs.getString("Note"));
                order.setShippingFee(rs.getFloat("ShippingFee"));
                order.setPaymentMethod(rs.getString("PaymentMethod"));
                order.setStatuspayment(rs.getString("PaymentStatus"));
                order.setTotalAmount(rs.getDouble("TotalAmount"));
                order.setStatus(rs.getString("Status"));
                // Add logic to retrieve cart items if needed
                return order;
            });
        } catch (Exception e) {
            // Ghi log nếu cần
            return new ArrayList<>();
        }
    }

    // Phương thức để lấy tất cả các sản phẩm trong giỏ hàng theo ID đơn hàng
    public List<CartItem> getCartItemsByOrderId(String orderId) {
        String sqlString = "SELECT * FROM OrderDetails WHERE OrderID = ?";
        return template.query(sqlString, (rs, rowNum) -> {
            CartItem cartItem = new CartItem();
            cartItem.setIDItem(rs.getString("ProductID"));
            cartItem.setQuantity(rs.getInt("Quantity"));
            cartItem.setGiasp(rs.getDouble("Price") * rs.getInt("Quantity"));
            cartItem.setItem(productDAO.FindProduct1(rs.getString("ProductID")));
            // Add logic to retrieve other fields if needed
            return cartItem;
        }, orderId);
    }

    // Phương thức để lấy đơn hàng theo ID
    public Order getOrderById(String orderId) {
        // Implement your logic to retrieve an order from the database by its ID
        String sql = "SELECT * FROM Orders WHERE OrderID = ?";

        try {
            return template.queryForObject(sql, (rs, rowNum) -> {
                Order ordertemp = new Order();
                ordertemp.setOrderId(rs.getString("OrderID"));
                ordertemp.setUserId(rs.getString("UserID"));
                ordertemp.setOrderDate(rs.getString("OrderDate"));
                ordertemp.setNote(rs.getString("Note"));
                ordertemp.setShippingFee(rs.getFloat("ShippingFee"));
                ordertemp.setPaymentMethod(rs.getString("PaymentMethod"));
                ordertemp.setStatuspayment(rs.getString("PaymentStatus"));
                ordertemp.setTotalAmount(rs.getDouble("TotalAmount"));
                ordertemp.setStatus(rs.getString("Status"));
                ordertemp.setCartItems(getCartItemsByOrderId(orderId));
                for (CartItem item : ordertemp.getCartItems()) {
                    Product temp = productDAO.getProduct(item.getIDItem1()) != null
                            ? productDAO.getProduct(item.getIDItem1())
                            : null;
                    if (temp != null) {
                        item.setItem(temp);
                        item.getItem().setListIMG(productDAO.getListIMG(temp));
                    }
                }
                return ordertemp;
            }, orderId);
        } catch (Exception e) {
            // Ghi log nếu cần
            return null;
        }
    }

    public List<Order> getAllOrderbyUserID(String idUser) {
        String sql = "SELECT * FROM Orders WHERE UserID = ?";
        try {
            return template.query(sql, (rs, rowNum) -> {
                Order ordertemp = new Order();
                ordertemp.setOrderId(rs.getString("OrderID"));
                ordertemp.setUserId(rs.getString("UserID"));
                // Nên đổi kiểu nếu cột là DATETIME
                ordertemp.setOrderDate(rs.getString("OrderDate"));
                ordertemp.setNote(rs.getString("Note"));
                ordertemp.setShippingFee(rs.getFloat("ShippingFee"));
                ordertemp.setPaymentMethod(rs.getString("PaymentMethod"));
                ordertemp.setStatuspayment(rs.getString("PaymentStatus"));
                ordertemp.setTotalAmount(rs.getDouble("TotalAmount"));
                ordertemp.setStatus(rs.getString("Status"));

                List<CartItem> items = getCartItemsByOrderId(ordertemp.getOrderId());
                if (items == null) {
                    items = new ArrayList<>();
                }
                System.out.println(items.toString());
                for (CartItem item : items) {

                    Product temp = productDAO.getProduct(item.getIDItem1());
                    if (temp != null) {
                        item.setItem(temp);
                        temp.setListIMG(productDAO.getListIMG(temp));
                    }
                }
                ordertemp.setCartItems(items);
                System.out.println(ordertemp.toString());
                return ordertemp;
            }, idUser);
        } catch (Exception e) {
            e.printStackTrace(); // hoặc log lỗi
            return new ArrayList<>();
        }
    }

}
