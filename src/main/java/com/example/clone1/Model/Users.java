package com.example.clone1.Model;

public class Users {
    private String Id, Name, Date, Gender, Role, Email, Phonenumber, Address;
    private String Password;
    private String Img;
    private String Mat_khau;
    private int totalBuy;
    private String Createat;

    public Users() {
        this.Role = "Customer";
    }

    public boolean isCheckPassword() {
        if (Password == null || Mat_khau == null)
            return true;
        return Password.equals(Mat_khau);
    }

    public Users(String Id, String Name, String Email, String Phonenumber, String Address,
            String Password, String Role, String Createat) {
        this.Id = Id;
        this.Name = Name;
        this.Role = Role;
        this.Email = Email;
        this.Phonenumber = Phonenumber;
        this.Address = Address;
        this.Password = Password;
        this.Createat = Createat;
    }

    public Users(String id, String name, String email, String matkhau, String phonenumber, String address,
            String role) {
        this.Id = id;
        this.Name = name;
        this.Email = email;
        this.Mat_khau = matkhau;
        this.Phonenumber = phonenumber;
        this.Address = address;
        this.Role = role;
    }

    public Users(String Id, String Name, String Date, String Gender, int totalBuy) {
        this.Id = Id;
        this.Name = Name;
        this.Date = Date;
        this.Gender = Gender;
        this.totalBuy = totalBuy;
    }

    public Users(String Id, String Name, String Date, String Gender, String Role, String Email, String Phonenumber,
            String Address, String Password, String Img, String Mat_khau, String Mat_khau2, int totalBuy) {
        this.Id = Id;
        this.Name = Name;
        this.Date = Date;
        this.Gender = Gender;
        this.Role = Role;
        this.Email = Email;
        this.Phonenumber = Phonenumber;
        this.Address = Address;
        this.Password = Password;
        this.Img = Img;
        this.Mat_khau = Mat_khau;
        this.totalBuy = totalBuy;
    }

    public String getCreateat() {
        return this.Createat;
    }

    public void setCreateat(String Createat) {
        this.Createat = Createat;
    }

    @Override
    public String toString() {
        return "{" +
                " Id='" + getId() + "'" +
                ", Name='" + getName() + "'" +
                ", Date='" + (getDate() == null || getDate().isEmpty() ? "NULL" : getDate()) + "'" +
                ", Gender='" + getGender() + "'" +
                ", Role='" + getRole() + "'" +
                ", Email='" + getEmail() + "'" +
                ", Phonenumber='" + getPhonenumber() + "'" +
                ", Address='" + getAddress() + "'" +
                ", Password='" + getPassword() + "'" +
                ", Mat_khau='" + getMat_khau() + "'" +
                ", totalBuy='" + getTotalBuy() + "'" +
                "}";
    }

    public String getId() {
        return this.Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getName() {
        return this.Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getDate() {
        return this.Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public String getGender() {
        return this.Gender;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    public String getRole() {
        return this.Role;
    }

    public void setRole(String Role) {
        this.Role = Role;
    }

    public String getEmail() {
        return this.Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPhonenumber() {
        return this.Phonenumber;
    }

    public void setPhonenumber(String Phonenumber) {
        this.Phonenumber = Phonenumber;
    }

    public String getAddress() {
        return this.Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getPassword() {
        return this.Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getImg() {
        return this.Img;
    }

    public void setImg(String Img) {
        this.Img = Img;
    }

    public String getMat_khau() {
        return this.Mat_khau;
    }

    public void setMat_khau(String Mat_khau) {
        this.Mat_khau = Mat_khau;
    }

    public int getTotalBuy() {
        return this.totalBuy;
    }

    public void setTotalBuy(int totalBuy) {
        this.totalBuy = totalBuy;
    }

}
