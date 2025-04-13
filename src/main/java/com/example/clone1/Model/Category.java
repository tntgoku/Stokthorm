package com.example.clone1.Model;

import java.util.Objects;

public class Category {
    String Name;
    int id;

    public Category() {
    }

    public Category(int id, String Name) {
        this.id = id;
        this.Name = Name;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public Category id(int id) {
        setId(id);
        return this;
    }

    public Category Name(String Name) {
        setName(Name);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Category)) {
            return false;
        }
        Category category = (Category) o;
        return Objects.equals(id, category.id) && Objects.equals(Name, category.Name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, Name);
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", Name='" + getName() + "'" +
                "}";
    }

}
