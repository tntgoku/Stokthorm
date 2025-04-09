package com.example.clone1.Model;

public class CartItem {
    private String IDItem;
    private int Quantity;
    private Product item;
    private double giasp;

    public CartItem() {
    }

    public CartItem(String IDItem, int Quantity) {
        this.IDItem = IDItem;
        this.Quantity = Quantity;
    }

    public CartItem(String IDItem, int Quantity, Product item) {
        this.IDItem = IDItem;
        this.Quantity = Quantity;
        this.item = item;
        this.giasp = Quantity * (item.getPrice() - (item.getPrice() * (item.getDiscount() / 100)));
    }

    public double getGiasp() {
        return this.giasp;
    }

    public double getThanhTien() {
        return Quantity * (item.getPrice() - (item.getPrice() * (item.getDiscount() / 100)));
    }

    public void setGiasp(double giasp) {
        this.giasp = giasp;
    }

    public String getIDItem1() {
        return this.IDItem;
    }

    public String getIDItem() {
        return item.getID();
    }

    public void setIDItem(String IDItem) {
        this.IDItem = IDItem;
    }

    public int getQuantity() {
        return this.Quantity;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

    public Product getItem() {
        return this.item;
    }

    public void setItem(Product item) {
        this.item = item;
    }

    public CartItem IDItem(String IDItem) {
        setIDItem(IDItem);
        return this;
    }

    public CartItem Quantity(int Quantity) {
        setQuantity(Quantity);
        return this;
    }

    public CartItem item(Product item) {
        setItem(item);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
                " IDItem='" + (getIDItem1() == null ? ' ' : getIDItem1()) + "'" +
                ", Quantity='" + getQuantity() + "'" +
                ", item='" + getItem() + "'" +
                "}";
    }

}