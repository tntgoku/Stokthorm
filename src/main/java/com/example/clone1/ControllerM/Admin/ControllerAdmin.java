package com.example.clone1.ControllerM.Admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ControllerAdmin {

    @GetMapping("/admin")
    public String gethomepageadmin(Model model) {
        System.out.println("Welecome Go to Home Page View Admin");
        return "admin/homepage";
    }

}
