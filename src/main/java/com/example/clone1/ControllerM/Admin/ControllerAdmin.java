package com.example.clone1.ControllerM.Admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.clone1.Model.Users;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ControllerAdmin {

    @GetMapping("/admin")
    public String gethomepageadmin(Model model) {
        System.out.println("Welecome Go to Home Page View Admin");
        return "admin/homepage";
    }

    @GetMapping("/info-Acc")
    public String getinfome(@RequestParam("id") String UsersID, Model model, HttpSession session) {
        if (UsersID == null) {
            return "redirect:/";
        }
        Users users = (Users) session.getAttribute("taikhoan");
        if (users == null) {
            return "redirect:/";
        }
        model.addAttribute("taikhoan", users);
        return "infome";
    }

    @PostMapping("/change")
    public String changeProfile(
            @RequestParam("tk_id") String id,
            @RequestParam("ho_ten") String name,
            @RequestParam("email") String email,
            @RequestParam("mat_khau") String password,
            @RequestParam("sdt") String phone,
            @RequestParam("dia_chi") String address) {
        // Xử lý cập nhật thông tin người dùng ở đây, ví dụ:
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phone);
        System.out.println("Address: " + address);

        // redirect lại trang thông tin người dùng
        return "redirect:/info-Acc?id=" + id;
    }

}
