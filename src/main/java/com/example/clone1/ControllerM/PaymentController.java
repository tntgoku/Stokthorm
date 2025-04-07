package com.example.clone1.ControllerM;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.clone1.Model.CartItem;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/payment")
public class PaymentController {
    // Implement your payment processing logic here
    // For example, you can use a payment gateway API to handle payments
    // and update the order status in your database accordingly.
    // This is just a placeholder for the actual implementation.

    @GetMapping
    public static String gotoPayment(Model model, HttpSession session) {
        List<CartItem> cartItems = (List<CartItem>) session.getAttribute("selectedItems"); // Giả lập danh sách sản phẩm
        if (cartItems == null)
            cartItems = new ArrayList<>();
        double totalAmount = 0.0;
        for (CartItem item : cartItems) {
            totalAmount += item.getGiasp() * (double) item.getQuantity(); // Tính tổng tiền
        }
        model.addAttribute("selectedItems", cartItems);
        model.addAttribute("totalAmount", totalAmount); // Thêm tổng tiền vào mô hình
        return "payment";
    }

    @PostMapping("checkpayment")
    public ResponseEntity<String> postMethodName(@RequestBody Map<String, Object> requestData, HttpSession session) {
        System.out.println("Sản phẩm thêm vào giỏ hàng: " + requestData);
        // TODO: process POST request
        String l = requestData.get("ten") != null ? (String) requestData.get("ten") : "";
        String l1 = requestData.get("sdt") != null ? (String) requestData.get("sdt") : "";
        String l2 = requestData.get("diachi") != null ? (String) requestData.get("diachi") : "";
        String l3 = requestData.get("method") != null ? (String) requestData.get("method") : "";
        if (l3.equals("payment")) {
        }
        return ResponseEntity.ok("/payment"); // Redirect to the payment page after processing
    }

}
