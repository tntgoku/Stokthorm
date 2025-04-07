package com.example.clone1.Model;

public class Users {
    private String Id, Name, Date, Gender, Role, Email, Phonenumber, Address;
    private String Password;
    private String Img;
    private String Mat_khau;
    private String Mat_khau2;
    private int totalBuy;

    public Users() {
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
        this.Mat_khau2 = Mat_khau2;
        this.totalBuy = totalBuy;
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
        return this.Phonenumber == null ? "NULL" : this.Phonenumber;
    }

    public void setPhonenumber(String Phonenumber) {
        this.Phonenumber = Phonenumber;
    }

    public String getAddress() {
        return this.Address == null ? "NULL" : this.Address;
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

    public String getMat_khau2() {
        return this.Mat_khau2;
    }

    public void setMat_khau2(String Mat_khau2) {
        this.Mat_khau2 = Mat_khau2;
    }

    public int getTotalBuy() {
        return this.totalBuy;
    }

    public void setTotalBuy(int totalBuy) {
        this.totalBuy = totalBuy;
    }

}
