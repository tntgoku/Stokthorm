package com.example.clone1.DAO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
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
                "Pro.Description as chuthich , Pro.ProductID " +
                "FROM Products Pro " +
                "INNER JOIN ProductAttributes Proa ON Pro.ProductID = Proa.ProductID " +
                "INNER JOIN Categories cat ON Pro.CategoryID = cat.CategoryID " +
                "LEFT JOIN Promotions discount ON Pro.ProductID = discount.ProductID";

        return template.query(sql, (rs, rowNum) -> new Product(
                rs.getString("ID"),
                rs.getString("Name"),
                rs.getInt("CategoryID"),
                rs.getFloat("Price"),
                rs.getInt("Quantity"),
                rs.getString("Size"),
                rs.getString("Color"),
                rs.getFloat("Discount"),
                rs.getString("chuthich"),
                "default.jpg", rs.getInt("ProductID")));
    }

    public List<Product> getAllProductByCate(int categoryId) {
        String sql = "SELECT * FROM productas WHERE CategoryID = ?";
        return template.query(sql, new RowMapper<Product>() {
            @Override
            public Product mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
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
                        "default.jpg", rs.getInt("ProductID"));
            }
        }, categoryId);
    }

    public Map<String, Object> FindProduct(String id) {
        String sql = "SELECT * FROM products Where  ProductID = ?";
        return template.queryForMap(sql, id);
    }

    // Bảng View
    public Product FindProduct1(String id) {
        String sql = "SELECT * FROM productas WHERE ID = ?";
        try {
            return template.queryForObject(sql, (rs, rowNum) -> new Product(
                    rs.getString("ID"),
                    rs.getString("Name"),
                    rs.getInt("CategoryID"),
                    rs.getFloat("Price"),
                    rs.getInt("Quantity"),
                    rs.getString("Size"),
                    rs.getString("Color"),
                    rs.getFloat("Discount"),
                    rs.getString("chuthich"),
                    "default.jpg", rs.getInt("ProductID")), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Product getProduct(String id) {
        String sql = "SELECT * FROM productas WHERE ID = ?";
        try {
            return template.queryForObject(sql, (rs, rowNum) -> new Product(
                    rs.getString("ID"),
                    rs.getString("Name"),
                    rs.getInt("CategoryID"),
                    rs.getFloat("Price"),
                    rs.getInt("Quantity"),
                    rs.getString("Size"),
                    rs.getString("Color"),
                    rs.getFloat("Discount"),
                    rs.getString("chuthich"),
                    "default.jpg", rs.getInt("ProductID")), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<String> getListIMG(Product pro) {
        String id = pro.getID();
        String sql = "SELECT * FROM ListIMG WHERE IDP = ?";
        return template.query(sql, (@NonNull ResultSet rs, int rowNum) -> rs.getString(3), id);
    }

    public List<String> getListSize(Product pro) {
        String id = pro.getID();
        String sql = "SELECT size FROM ProductAttributes WHERE ProductID = ?";
        return template.query(sql, (rs, rowNum) -> rs.getString(1), id);
    }

    public List<String> getListColor(Product pro) {
        String id = pro.getID();
        String sql = "SELECT Color FROM ProductAttributes WHERE ProductID = ?";
        return template.query(sql, (rs, rowNum) -> rs.getString(1), id);
    }

    public List<String> getListQuantity(Product pro) {
        String id = pro.getID();
        String sql = "SELECT StockQuantity FROM ProductAttributes WHERE ProductID = ?";
        return template.query(sql, (rs, rowNum) -> rs.getString(1), id);
    }

    public int updateQuantityPro(Product pro, int quantity) {
        String updateAttrSql = "UPDATE ProductAttributes SET StockQuantity = ? WHERE AttributeID = ?";
        String selectSumSql = "SELECT SUM(StockQuantity) FROM ProductAttributes WHERE ProductID = ?";
        String updateProSql = "UPDATE Products SET StockQuantity = ? WHERE ProductID = ?";

        try {
            // 1. Cập nhật số lượng ProductAttributes
            int status = template.update(updateAttrSql, pro.getQuantity() - quantity, pro.getID());
            if (status == 0) {
                return -1; // Không cập nhật được
            }

            // 2. Tính tổng số lượng tồn của các thuộc tính theo ProductID
            Integer newTotal = template.queryForObject(selectSumSql, Integer.class, pro.getCategoryID());
            int updated = -1;
            if (newTotal != null) {
                updated = template.update(updateProSql, newTotal, pro.getCategoryID());
            }

            return (updated > 0) ? updated : -1;

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int updateProduct(String id, String color, String size, double price, String desc, String name, int categori,
            int quantity, double discount) {
        String sql = "UPDATE  ProductAttributes SET StockQuantity = ?, Color= ?, Size =?  WHERE AttributeID = ?";
        String selectSumSql = "SELECT SUM(StockQuantity) FROM ProductAttributes WHERE ProductID = ?";
        String sql1 = "UPDATE Products SET   ProductName = ? , Description = ? , Price = ? , StockQuantity = ? , CategoryID = ? WHERE ProductID = ?";
        String sql2 = "UPDATE Promotions SET  DiscountPersent = ? WHERE ProductID = ?";
        try {
            int status = template.update(sql, quantity, color, size, id);
            if (status == 0)
                return -1;
            Integer newTotal = template.queryForObject(selectSumSql, Integer.class, categori);
            int status1 = template.update(sql1, name, desc, price, newTotal, categori, id);
            if (status1 == 0)
                return -1;
            int status2 = template.update(sql2, discount);
            if (status2 == 0)
                return -1;
            // int status= template.update(sql, pro.getQuantity()-,)
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
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
            e.printStackTrace();
            return -1;
        }
    }

    // Cái này ở đây dùng Proc
    public int deleteProduct(int ID) {
        String sql = "{call xoasanpham(?)}";
        try {

            return template.update(sql, ID);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public List<Product> getAllProducts(int page, int size) {
        int offset = page * size;
        String sql = "SELECT * FROM productas ORDER BY ID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try {
            return template.query(sql, (rs, rowNum) -> new Product(
                    rs.getString("ID"),
                    rs.getString("Name"),
                    rs.getInt("CategoryID"),
                    rs.getFloat("Price"),
                    rs.getInt("Quantity"),
                    rs.getString("Size"),
                    rs.getString("Color"),
                    rs.getFloat("Discount"),
                    rs.getString("chuthich"),
                    "/assets/img/product/1733800842_vn-11134207-7r98o-lwpnuez7m7dn51.jpg",
                    rs.getInt("ProductID")),
                    offset, size); // offset trước, size sau
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public int addProduct(Product pro) {
        String sql = "INSERT INTO Products (ProductID,ProductName,Description,Price,StockQuantity,CategoryID) Values (?, ?, ?, ?, ?, ?)";
        String sql1 = "INSERT INTO ProductAttributes (ProductID,Color,Size,StockQuantity) VALUES ( ?, ?, ?, ?)";
        String sql2 = "INSERT INTO ListIMG (IDP, img) VALUES(?, ?)";
        String sql3 = "InSERT INTO Promotions  (ProductID, DiscountPercent,StartDate,EndDate) VALUES(?, ?, ?, ?) ";

        BigDecimal discountRounded = new BigDecimal(pro.getDiscount()).setScale(2, RoundingMode.HALF_UP);

        String idcate = String.valueOf(pro.getCategoryID());
        System.out.println(idcate);
        try {
            int status1 = template.update(sql, pro.getID(), pro.getName(), pro.getDescription(), pro.getPrice(),
                    pro.getQuantity(), pro.getCategoryID());

            String timestampString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()).toString();
            template.update(sql3, pro.getID(), discountRounded, timestampString, timestampString);
            if (status1 != -1) {
                int status2 = template.update(sql1, pro.getID(), pro.getColor(),
                        pro.getSize(),
                        pro.getQuantity());

                if (status2 != -1) {
                    Integer stt = template.queryForObject(
                            "SELECT TOP 1 attributeid FROM ProductAttributes ORDER BY attributeid DESC", Integer.class);
                    if (stt != null) {

                        if (pro.getListIMG() != null && !pro.getListIMG().isEmpty()) {
                            for (int i = 0; i < pro.getListIMG().size(); i++) {
                                template.update(sql2, stt, pro.getListIMG().get(i));
                            }
                        }
                    }
                }
                return status2;
            } else {
                return -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int top1Product() {

        Integer total = template.queryForObject("select top 1 productid from Products order by productid desc",
                Integer.class);
        return (total != null) ? total : 0;
    }

    public int countallProduct() {
        Integer total = template.queryForObject("SELECT COUNT(*) FROM  productas", Integer.class);

        return (total != null) ? total : 0;
    }

}
