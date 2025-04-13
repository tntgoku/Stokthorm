package com.example.clone1.DAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.clone1.Model.Category;

@Repository
public class CategoryDAO {
    @Autowired
    private JdbcTemplate template;

    public List<Category> getallCate() {
        return template.query("SELECT * FROM Categories order by  CategoryID desc; ",
                (rs, rowNum) -> new Category(rs.getInt("CategoryID"), rs.getString("CategoryName")));
    }

    public int addCate(String name) {
        String sql = "INSERT INTO Categories (CategoryName) VALUES(?)";
        try {
            return template.update(sql, name);
        } catch (Exception e) {
            return -1;
        }
    }

    public int UpdateCate(String name, String ID) {
        String sql = "UPDATE  Categories SET  CategoryName = ? WHERE CategoryID = ?";
        try {
            return template.update(sql, name, ID);
        } catch (Exception e) {
            return -1;
        }
    }

    public int DeleteCate(String name) {
        String sql = "DELETE FROM Categories WHERE CategoryID = ?";
        try {
            return template.update(sql, name);
        } catch (Exception e) {
            return -1;
        }
    }
}
