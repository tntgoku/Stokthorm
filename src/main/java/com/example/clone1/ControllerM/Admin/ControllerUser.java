package com.example.clone1.ControllerM.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.clone1.DAO.UserDAO;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class ControllerUser {

    @Autowired
    private UserDAO userDAO;

    @GetMapping("/listUser")
    public String Pagelistuser(Model model, HttpSession session,
            @RequestParam(value = "id", required = false) String Id,
            @RequestParam(value = "act", required = false) String act) {

        model.addAttribute("listUser", userDAO.GetAllUser1());
        return "admin/users/listuser";
    }

}
