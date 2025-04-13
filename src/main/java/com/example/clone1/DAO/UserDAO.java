package com.example.clone1.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.example.clone1.Model.Users;

@Repository
public class UserDAO {
    // Your UserDAO implementation here
    // This class should contain methods to interact with the database for
    // user-related operations
    // For example, methods to create, read, update, and delete users
    // You can use JdbcTemplate or any other ORM framework as per your requirement
    @Autowired
    private JdbcTemplate template;

    public List<Users> GetAllUser() {
        String sql = "SELECT * FROM Users";
        return template.query(sql, new RowMapper<Users>() {
            @Override
            public Users mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
                return new Users(
                        rs.getString("UserID"),
                        rs.getString("FullName"),
                        rs.getString("Email"),
                        rs.getString("Pwd"),
                        rs.getString("PhoneNumber"),
                        rs.getString("Address"),
                        rs.getString("Role"));
            }
        });
    }

    // Dung cho trang admin
    public List<Users> GetAllUser1() {
        String sql = "SELECT * FROM Users";
        return template.query(sql, new RowMapper<Users>() {
            @Override
            public Users mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
                Users s = new Users();
                s.setId(rs.getString("UserID"));
                s.setName(rs.getString("FullName"));
                s.setEmail(rs.getString("Email"));
                s.setPhonenumber("PhoneNumber");
                s.setAddress(rs.getString("Address"));
                s.setPassword(rs.getString("Pwd"));
                s.setRole(rs.getString("Role"));
                s.setCreateat(rs.getString("CreatedAt"));
                String sql = "SELECT SUM(o.TotalAmount) FROM Users s JOIN Orders o ON s.UserID = o.UserID WHERE s.UserID = ?";
                Integer total = template.queryForObject(sql, Integer.class, s.getId());

                s.setTotalBuy((total != null) ? total : 0);
                return s;
            }
        });
    }

    // Example method to get a user by ID
    public Users getUserById(String id) {
        String sql = "SELECT * FROM Users WHERE UserID = ?";
        return template.queryForObject(sql, (rs, rowNum) -> {
            Users user = new Users();
            user.setId(rs.getString("UserID"));
            user.setName(rs.getString("FullName"));
            user.setEmail(rs.getString("Email"));
            user.setPassword(rs.getString("Pwd"));
            user.setPhonenumber(rs.getString("PhoneNumber"));
            user.setAddress(rs.getString("Address"));
            user.setRole(rs.getString("Role"));
            return user;
        }, id);
    }

    public String getLastUserID() {
        String sql = "SELECT TOP 1 UserID FROM Users ORDER BY UserID DESC";
        try {
            return template.queryForObject(sql, String.class);
        } catch (EmptyResultDataAccessException e) {
            // Handle case where no result is found, return null or a default value
            System.out.println("No orders found in the database.");
            return null; // or throw a custom exception
        }
    }

    // Example method to create a new user
    public int createUser(Users user) {
        String sql = "INSERT INTO Users (UserID, FullName, Email, PhoneNumber, Address, Pwd, Role) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            return template.update(sql, user.getId(), user.getName(), user.getEmail(), user.getPhonenumber(),
                    user.getAddress(), user.getPassword(), user.getRole());
        } catch (DataAccessException e) {
            System.err.println("Error inserting user: " + e.getMessage());
            return -1;
        }
    }

    // Example method to update an existing user
    public void updateUser(Users user) {
        // Implementation here
        String sql = "UPDATE Users SET FullName = ?, Email = ?, PhoneNumber = ?, Address = ?, Pwd = ?, Role = ? WHERE UserID = ?";
        try {
            template.update(sql, user.getName(), user.getEmail(), user.getPhonenumber(),
                    user.getAddress(), user.getPassword(), user.getRole(), user.getId());
            System.out.println("User updated successfully!");
        } catch (DataAccessException e) {
            System.err.println("Error updating user: " + e.getMessage());
        }
    }

    public boolean checkEmail(String email) {
        String sql = "SELECT TOP 1 Email FROM users WHERE Email = ?";
        try {
            String result = template.queryForObject(sql, String.class, email);
            return result != null;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean checkPhone(String email) {
        String sql = "SELECT TOP 1 PhoneNumber FROM users WHERE PhoneNumber = ?";
        try {
            String result = template.queryForObject(sql, String.class, email);
            return result != null;
        } catch (Exception e) {
            return false;
        }
    }

    // Example method to delete a user by ID
    public void deleteUser(String id) {
        // Implementation here
        String sql = "DELETE FROM Users WHERE UserID = ?";
        try {
            template.update(sql, id);
            System.out.println("User deleted successfully!");
        } catch (DataAccessException e) {
            System.err.println("Error deleting user: " + e.getMessage());
        }
    }

    public int checkLogin(String email, String password) {
        String sql = "SELECT COUNT(*) FROM Users WHERE Email = ? AND Pwd = ?";
        return template.queryForObject(sql, Integer.class, email, password);
    }

    public Users getUserByEmail(String email) {
        String sql = "SELECT * FROM Users WHERE Email = ?";
        return template.queryForObject(sql, (rs, rowNum) -> {
            Users user = new Users();
            user.setId(rs.getString("UserID"));
            user.setName(rs.getString("FullName"));
            user.setEmail(rs.getString("Email"));
            user.setPassword(rs.getString("Pwd"));
            user.setPhonenumber(rs.getString("PhoneNumber"));
            user.setAddress(rs.getString("Address"));
            user.setRole(rs.getString("Role"));
            return user;
        }, email);
    }
}
