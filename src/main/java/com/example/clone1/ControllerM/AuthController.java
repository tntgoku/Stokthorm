package com.example.clone1.ControllerM;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.clone1.DAO.UserDAO;
import com.example.clone1.Model.Users;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/login")
public class AuthController {
    @Autowired
    ControllerM controller;
    @Autowired
    UserDAO userDAO;

    @PostMapping("/check-login")
    public ResponseEntity<Map<String, Object>> checkLogin(@RequestBody Map<String, Object> loginRequest,
            HttpSession session, Model model) {

        String email = (String) loginRequest.get("email");
        String mat_khau = (String) loginRequest.get("mat_khau");
        Boolean rememberMe = (Boolean) loginRequest.get("rememberMe");

        Map<String, Object> response = new HashMap<>();
        System.out.println("Email: " + email);
        System.out.println("Password: " + mat_khau);
        System.out.println("Remember Me: " + rememberMe);

        if (userDAO.checkLogin(email, mat_khau) != 1) {
            response.put("success", false);
            response.put("message", "Email hoặc mật khẩu không đúng!");
            model.addAttribute("error", "Email hoặc mật khẩu không đúng!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        } else {
            Users a = userDAO.getUserByEmail(email);
            session.setAttribute("taikhoan", a);// Lưu vào session
            if (a.getRole().equals("admin")) {
                response.put("homepage", "/admin");
                response.put("message", "Đăng nhập thành công Admin!");
            } else {
                response.put("homepage", "/");
                response.put("message", "Đăng nhập thành công!");
            }
            // Xử lý "Ghi nhớ tài khoản"
            if (rememberMe != null && rememberMe) {
                Cookie emailCookie = new Cookie("email", email);
                Cookie passCookie = new Cookie("password", mat_khau);
                emailCookie.setMaxAge(7 * 24 * 60 * 60); // Lưu 7 ngày
                passCookie.setMaxAge(7 * 24 * 60 * 60);
                response.put("rememberMe", true);
                return ResponseEntity.ok(response);
            } else {
                Cookie emailCookie = new Cookie("email", "");
                Cookie passCookie = new Cookie("password", "");
                emailCookie.setMaxAge(0); // Xóa cookie
                passCookie.setMaxAge(0);
            }
            response.put("success", true);
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/remove-remember")
    public String removeRemember(HttpServletResponse response) {
        Cookie emailCookie = new Cookie("email", "");
        Cookie passCookie = new Cookie("password", "");
        emailCookie.setMaxAge(0);
        passCookie.setMaxAge(0);
        response.addCookie(emailCookie);
        response.addCookie(passCookie);
        return "redirect:/auth/login";
    }

}
