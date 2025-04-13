package com.example.clone1.ControllerM;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.clone1.Model.Users;
import com.example.clone1.Service.UserRegisterDTO;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    UserRegisterDTO userSV;

    @GetMapping
    public String showForm(Model model) {
        model.addAttribute("userRegister", new Users());
        return "register"; // trỏ tới view register.html
    }

    @PostMapping("/checkregister")
    @ResponseBody
    public ResponseEntity<?> checkRegister(@ModelAttribute Users user) {
        Map<String, String> errors = new HashMap<>();

        if (user.getName() == null || user.getName().isEmpty()) {
            errors.put("name", "Tên không được để trống");
        }

        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            errors.put("email", "Email không được để trống");
        } else if (userSV.checkEmail(user.getEmail())) {
            errors.put("email", "Email đã tồn tại, vui lòng nhập email khác");
        }

        if (user.getPhonenumber() == null || user.getPhonenumber().isEmpty()) {
            errors.put("phonenumber", "Số điện thoại không được để trống");
        }

        if (user.getAddress() == null || user.getAddress().isEmpty()) {
            errors.put("address", "Địa chỉ không được để trống");
        }

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            errors.put("password", "Mật khẩu không được để trống");
        }

        if (user.getMat_khau() == null || user.getMat_khau().isEmpty()) {
            errors.put("mat_khau", "Vui lòng nhập lại mật khẩu");
        } else if (!user.getMat_khau().equals(user.getPassword())) {
            errors.put("checkPassword", "Mật khẩu nhập lại không khớp");
        }

        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("errors", errors));
        }
        userSV.saveUser(user);
        return ResponseEntity.ok().build(); // 200 OK nếu thành công
    }

}
