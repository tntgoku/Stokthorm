package com.example.clone1.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.clone1.Model.Product;

@Repository
public class ProductDAO {
    @Autowired
    private JdbcTemplate template;

    public List<Product> getAllProduct() {
        String sql = "SELECT Proa.AttributeID as ID, " +
                "Pro.ProductName as Name, " +
                "cat.CategoryID as CategoryID, " +
                "Pro.Price, " +
                "Proa.StockQuantity as Quantity, " +
                "Proa.Color, " +
                "Proa.Size, " +
                "discount.DiscountPercent as Discount, " +
                "Pro.Description as chuthich " +
                "FROM Products Pro " +
                "INNER JOIN ProductAttributes Proa ON Pro.ProductID = Proa.ProductID " +
                "INNER JOIN Categories cat ON Pro.CategoryID = cat.CategoryID " +
                "LEFT JOIN Promotions discount ON Pro.ProductID = discount.ProductID";

        return template.query(sql, new RowMapper<Product>() {
            @Override
            public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Product(
                        rs.getString("ID"),
                        rs.getString("Name"),
                        rs.getInt("CategoryID"),
                        rs.getFloat("Price"),
                        rs.getInt("Quantity"),
                        rs.getString("Size"),
                        rs.getString("Color"),
                        rs.getFloat("Discount"),
                        rs.getString("chuthich"),
                        "default.jpg");
            }
        });
    }

    public List<Product> getAllProductByCate(int categoryId) {
        String sql = "SELECT * FROM productas WHERE CategoryID = ?";
        return template.query(sql, new Object[] { categoryId }, new RowMapper<Product>() {
            @Override
            public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Product(
                        rs.getString("ID"),
                        rs.getString("Name"),
                        rs.getInt("CategoryID"),
                        rs.getFloat("Price"),
                        rs.getInt("Quantity"),
                        rs.getString("Size"),
                        rs.getString("Color"),
                        rs.getFloat("Discount"),
                        rs.getString("chuthich"),
                        "default.jpg");
            }
        });
    }

    public Map<String, Object> FindProduct(String id) {
        String sql = "SELECT * FROM products Where  ProductID = ?";
        return template.queryForMap(sql, id);
    }

    public Product FindProduct1(String id) {
        String sql = "SELECT * FROM productas WHERE ID = ?";
        try {
            return template.queryForObject(sql, new Object[] { id }, new RowMapper<Product>() {
                @Override
                public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return new Product(
                            rs.getString("ID"), // Đảm bảo đúng tên cột
                            rs.getString("Name"),
                            rs.getInt("CategoryID"),
                            rs.getFloat("Price"),
                            rs.getInt("Quantity"),
                            rs.getString("Size"),
                            rs.getString("Color"),
                            rs.getFloat("Discount"),
                            rs.getString("chuthich"),
                            "default.jpg");
                }
            });
        } catch (EmptyResultDataAccessException e) {
            return null; // Trả về null nếu không tìm thấy sản phẩm
        }
    }

    public List<String> getListIMG(Product pro) {
        String id = pro.getID();
        String sql = "SELECT * FROM ListIMG WHERE IDP = ?";
        return template.query(sql, new Object[] { id }, (rs, rowNum) -> rs.getString(3));
    }

    public List<String> getListSize(Product pro) {
        String id = pro.getID();
        String sql = "SELECT size FROM ProductAttributes WHERE ProductID = ?";
        return template.query(sql, new Object[] { id }, (rs, rowNum) -> rs.getString(1));
    }

    public List<String> getListColor(Product pro) {
        String id = pro.getID();
        String sql = "SELECT Color FROM ProductAttributes WHERE ProductID = ?";
        return template.query(sql, new Object[] { id }, (rs, rowNum) -> rs.getString(1));
    }

    public List<String> getListQuantity(Product pro) {
        String id = pro.getID();
        String sql = "SELECT StockQuantity FROM ProductAttributes WHERE ProductID = ?";
        return template.query(sql, new Object[] { id }, (rs, rowNum) -> rs.getString(1));
    }

    public int updateQuantityPro(Product pro, int quantity) {
        String sql = "UPDATE  ProductAttributes SET StockQuantity = ? WHERE AttributeID = ?";
        try {
            int status = template.update(sql, pro.getQuantity() - quantity, pro.getID());
            if (status == 0) {
                return -1; // Nếu không thể cập nhật một sản phẩm, trả về -1
            }
            return status;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace(); // In lỗi ra console/log
            return -1;
        }
    }

    public int deletePro(Product pro) {
        String sql = "DELETE FROM ProductAttributes Where  AttributeID = ?";
        try {
            int status = template.update(sql, pro.getID());
            if (status == 0)
                return -1;
            return status;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return -1;
        }
    }
}
