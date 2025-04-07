package com.example.clone1.Model;

import java.util.List;
import java.util.Objects;

public class Order {
    private String orderId;
    private String userId;
    private String OrderDate;
    private String Note;
    private String PaymentMethod;
    private String status;
    private String Statuspayment;
    private double totalAmount;
    private List<CartItem> cartItems; // Danh sách sản phẩm trong đơn hàng

    public Order() {
    }

    // Không có ID đơn hàng
    public Order(String userId, String OrderDate, String Note, String PaymentMethod, String status,
            double totalAmount, List<CartItem> cartItems) {
        this.userId = userId;
        this.OrderDate = OrderDate;
        this.Note = Note;
        this.PaymentMethod = PaymentMethod;
        this.status = status;
        this.totalAmount = totalAmount;
        this.cartItems = cartItems;
    }

    public Order(String orderId, String userId, String OrderDate, String Note, String PaymentMethod, String status,
            double totalAmount, List<CartItem> cartItems) {
        this.orderId = orderId;
        this.userId = userId;
        this.OrderDate = OrderDate;
        this.Note = Note;
        this.PaymentMethod = PaymentMethod;
        this.status = status;
        this.totalAmount = totalAmount;
        this.cartItems = cartItems;
    }

    public String getStatuspayment() {
        return this.Statuspayment;
    }

    public void setStatuspayment(String Statuspayment) {
        this.Statuspayment = Statuspayment;
    }

    public String getOrderId() {
        return this.orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderDate() {
        return this.OrderDate;
    }

    public void setOrderDate(String OrderDate) {
        this.OrderDate = OrderDate;
    }

    public String getNote() {
        return this.Note;
    }

    public void setNote(String Note) {
        this.Note = Note;
    }

    public String getPaymentMethod() {
        return this.PaymentMethod;
    }

    public void setPaymentMethod(String PaymentMethod) {
        this.PaymentMethod = PaymentMethod;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotalAmount() {
        return this.totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<CartItem> getCartItems() {
        return this.cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public Order orderId(String orderId) {
        setOrderId(orderId);
        return this;
    }

    public Order userId(String userId) {
        setUserId(userId);
        return this;
    }

    public Order OrderDate(String OrderDate) {
        setOrderDate(OrderDate);
        return this;
    }

    public Order Note(String Note) {
        setNote(Note);
        return this;
    }

    public Order PaymentMethod(String PaymentMethod) {
        setPaymentMethod(PaymentMethod);
        return this;
    }

    public Order status(String status) {
        setStatus(status);
        return this;
    }

    public Order totalAmount(double totalAmount) {
        setTotalAmount(totalAmount);
        return this;
    }

    public Order cartItems(List<CartItem> cartItems) {
        setCartItems(cartItems);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Order)) {
            return false;
        }
        Order order = (Order) o;
        return Objects.equals(orderId, order.orderId) && Objects.equals(userId, order.userId)
                && Objects.equals(OrderDate, order.OrderDate) && Objects.equals(Note, order.Note)
                && Objects.equals(PaymentMethod, order.PaymentMethod) && Objects.equals(status, order.status)
                && totalAmount == order.totalAmount && Objects.equals(cartItems, order.cartItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, userId, OrderDate, Note, PaymentMethod, status, totalAmount, cartItems);
    }

    @Override
    public String toString() {
        return "{" +
                " orderId='" + getOrderId() + "'" +
                ", userId='" + getUserId() + "'" +
                ", OrderDate='" + getOrderDate() + "'" +
                ", Note='" + getNote() + "'" +
                ", PaymentMethod='" + getPaymentMethod() + "'" +
                ", status='" + getStatus() + "'" +
                ", totalAmount='" + getTotalAmount() + "'" +
                ", cartItems='" + getCartItems() + "'" +
                "}";
    }

}
