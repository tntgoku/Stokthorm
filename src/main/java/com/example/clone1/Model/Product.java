package com.example.clone1.Model;

import java.util.List;
import java.util.Objects;

public class Product {
    private String ID, Name, Size, Color, Description, Img;
    private float Price;
    private int CategoryID;
    private int Quantity;
    private float Discount;

    public List<String> listIMG;

    public Product() {
    }

    public Product(String iD, String name, int categoryID, float price,
            int quantity, String size, String color,
            float discount, String description, String img) {
        ID = iD;
        Name = name;
        CategoryID = categoryID;
        Price = price;
        Quantity = quantity;
        Size = size;
        Color = color;
        Img = img;
        Discount = discount;
        Description = description;
    }

    public Product(String iD, String name, int categoryID, float price,
            int quantity, String size, String color,
            float discount, String description, List<String> img) {
        ID = iD;
        Name = name;
        CategoryID = categoryID;
        Price = price;
        Quantity = quantity;
        Size = size;
        Color = color;
        this.listIMG = img;
        Discount = discount;
        Description = description;
    }

    public Product(String ID, String Name, int Quanity, String Img, String Description) {
        this.ID = ID;
        this.Name = Name;
        this.Quantity = Quanity;
        this.Img = Img;
        this.Description = Description;
    }

    public String getID() {
        return this.ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return this.Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getImg() {
        return this.Img;
    }

    public void setImg(String Img) {
        this.Img = Img;
    }

    public String getDescription() {
        return this.Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }

    public int getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(int CategoryID) {
        CategoryID = CategoryID;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public float getDiscount() {
        return Discount;
    }

    public void setDiscount(float discount) {
        Discount = discount;
    }

    public Product ID(String ID) {
        setID(ID);
        return this;
    }

    public Product Name(String Name) {
        setName(Name);
        return this;
    }

    public Product Img(String Img) {
        setImg(Img);
        return this;
    }

    public Product Description(String Description) {
        setDescription(Description);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Product)) {
            return false;
        }
        Product product = (Product) o;
        return Objects.equals(ID, product.ID) && Objects.equals(Name, product.Name);
    }

    @Override
    public String toString() {
        return "Product [ID=" + ID + ", Name=" + Name + ", Size=" + Size + ", Color=" + Color +
                ", Img=" + Img + ", Price=" + Price + ", CategoryID=" + CategoryID + ", Quantity="
                + Quantity + ", Discount=" + Discount + ", Description=" + Description +
                ", LISTIMG= " + (listIMG == null ? "NULL" : (listIMG.isEmpty() ? "Empty" : listIMG.toString())) + "]";
    }

    public List<String> getListIMG() {
        return listIMG;
    }

    public void setListIMG(List<String> listIMG) {
        this.listIMG = listIMG;
        System.out.println("List ảnh: " + listIMG.toString());
    }
}
