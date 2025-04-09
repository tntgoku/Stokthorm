package com.example.clone1.ControllerM;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.clone1.DAO.*;
import com.example.clone1.Model.*;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/orders")
public class HistoryController {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private ProductDAO productDAO;
    private String trangThai;
    @Autowired
    private OrderDAO orderdao;

    @GetMapping("/history")
    public String getHistoryPage(Model model, HttpSession session) {
        Users temp = (Users) session.getAttribute("taikhoan");
        if (temp == null) {
            return "redirect:/login";
        }
        System.out.println(temp.toString());
        List<Order> listOrder = orderdao.getAllOrderbyUserID(temp.getId());
        System.out.println(listOrder.toString());
        model.addAttribute("orders", listOrder);
        return "historyOrder";
    }

    @GetMapping("/chi-tiet-don-hang")
    public String chiTietDonHang(@RequestParam("id") String orderId, Model model) {
        System.out.println("OrderID:   " + orderId);
        Order order = orderdao.getOrderById(orderId);

        String moTaTrangThai = TrangThaiDonHang.fromString(order.getStatus()).getMoTa();
        model.addAttribute("order", order);
        model.addAttribute("trangThaiMoTa", moTaTrangThai);
        List<CartItem> items = order.getCartItems(); // hoặc order.getChiTietDonHang()
        model.addAttribute("productDonHang", items);
        return "orderdetail"; // tên file HTML để hiển thị
    }

    public String getTrangThaiMoTa() {
        return TrangThaiDonHang.fromString(trangThai).getMoTa();
    }
}
