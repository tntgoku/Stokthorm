package com.example.clone1.ControllerM;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.clone1.DAO.*;
import com.example.clone1.Model.*;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    UserDAO userDAO;
    @Autowired
    ProductDAO productDAO;
    @Autowired
    OrderDAO orderDAO;

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
        String method = requestData.get("method") != null ? (String) requestData.get("method") : "";
        System.out.println(method);
        session.setAttribute("dataRequest", requestData);
        if (method.equals("cod")) {

            return ResponseEntity.ok("/payment/thanksyou");
        }
        return ResponseEntity.ok("/payment"); // Redirect to the payment page after processing
    }

    @GetMapping("/thanksyou")
    public String GetPageThanks(HttpSession session, Model model) {
        System.out.println("Cảm ơn vì đã mua hàng "
                + DateFormat.getDateTimeInstance(0, 0, Locale.getDefault()).format(new Date()));

        Map<String, Object> requestData = (Map<String, Object>) session.getAttribute("dataRequest");
        String l = requestData.get("ten") != null ? (String) requestData.get("ten") : "";
        String l1 = requestData.get("sdt") != null ? (String) requestData.get("sdt") : "";
        String l2 = requestData.get("diachi") != null ? (String) requestData.get("diachi") : "";
        String l3 = requestData.get("method") != null ? (String) requestData.get("method") : "";
        Users user = (Users) session.getAttribute("taikhoan"); // Giả lập người dùng
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String timestampString = sdf.format(new Date());
        if (user == null) {
            user = new Users();
            int id = Integer.parseInt(userDAO.getLastUserID()) + 1; // Giả lập ID người dùng
            user.setId(String.valueOf(id)); // Tạo ID người dùng giả lập
            user.setName(l);
            user.setPhonenumber(l1);
            user.setAddress(l2);
            user.setPassword(l + user.getId() + timestampString.split(" ")[1]);
            String[] part;
            String lastname = null;
            part = l.trim().split(" ");
            if (part.length == 1)
                lastname = part[0];
            else if (part.length == 2)
                lastname = part[1];
            else
                lastname = part[part.length - 1];
            String[] datePart = timestampString.split(" "); // Lấy phần ngày
            user.setEmail(lastname + datePart[0] + datePart[1] + "@gmail.com");
            System.out.println(user.toString());
            userDAO.createUser(user);
        }
        List<CartItem> cartItems = (List<CartItem>) session.getAttribute("selectedItems"); // Giả lập danh sách sản phẩm
        double totalAmount = cartItems.stream().mapToDouble(item -> item.getGiasp() * (double) item.getQuantity())
                .sum(); // Tính tổng tiền
        String payments = l3.equals("payment") ? "VNPAY" : "COD";
        int id = Integer.parseInt(orderDAO.getLatestOrderId()); // Lấy ID đơn hàng mới nhất
        id++;
        if (orderDAO.createOrder(
                new Order(String.valueOf(id),
                        user.getId(),
                        timestampString,
                        l2 + "{/}" + timestampString,
                        payments,
                        "Pending",
                        totalAmount, cartItems)) == 1) // Tạo đơn hàng mới
        {
            System.out.println("Tạo đơn hàng thành công!");
            if (orderDAO.addOrderDetails(String.valueOf(id), cartItems) == 1) // Thêm sản phẩm vào đơn hàng
            {
                System.out.println("Thêm sản phẩm vào đơn hàng thành công!");
                // orderDAO.addtoPayment(order
            } else {
                System.out.println("Thêm sản phẩm vào đơn hàng thất bại!");
            }
        } else {
            System.out.println("Tạo đơn hàng thất bại!");
        }
        List<CartItem> listitem = (List<CartItem>) session.getAttribute("listcart");
        List<CartItem> listSelect = (List<CartItem>) session.getAttribute("selectedItems");

        // Duyệt qua từng item trong listSelect
        for (CartItem cartItem2 : listSelect) {
            Iterator<CartItem> iterator = listitem.iterator();
            while (iterator.hasNext()) {
                CartItem cartItem = iterator.next();
                // So sánh IDItem của cartItem và cartItem2
                if (cartItem.getIDItem().equals(cartItem2.getIDItem())) {
                    // Nếu trùng, xóa cartItem khỏi listitem
                    iterator.remove();
                    break; // Dừng vòng lặp nếu đã xóa một phần tử trùng
                }
            }
        }
        session.setAttribute("listcart", listitem);
        session.removeAttribute("selectedItems");
        return "thanksyou";
    }

}
