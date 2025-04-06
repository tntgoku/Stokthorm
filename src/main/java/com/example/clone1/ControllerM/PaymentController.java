package com.example.clone1.ControllerM;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/payment")
public class PaymentController {
    // Implement your payment processing logic here
    // For example, you can use a payment gateway API to handle payments
    // and update the order status in your database accordingly.
    // This is just a placeholder for the actual implementation.

    @GetMapping
    public String getMethodName(Model model, HttpSession session) {
        return "payment";
    }

}
