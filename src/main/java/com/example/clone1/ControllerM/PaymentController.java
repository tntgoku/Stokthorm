package com.example.clone1.ControllerM;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.clone1.DAO.*;
import com.example.clone1.Model.*;
import com.example.clone1.Service.VNPayService;

import jakarta.servlet.http.HttpServletRequest;
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
    @Autowired
    private VNPayService vnPayService;

    List<CartItem> lCartItems;

    @SuppressWarnings("unchecked")
    @GetMapping
    public String gotoPayment(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        lCartItems = (List<CartItem>) session.getAttribute("selectedItems"); // Giả lập danh sách sản phẩm
        if (lCartItems == null) {
            redirectAttributes.addFlashAttribute("alertMessage", "Giỏ hàng của bạn đang trống. Quay lại trang chủ.");
            return "redirect:/alert-redirect";
        }
        double totalAmount = lCartItems.stream().mapToDouble(
                item -> ((item.getItem().getPrice()
                        - (item.getItem().getPrice() * (item.getItem().getDiscount() / 100.0)))
                        * item.getQuantity()))
                .sum();
        System.out.println("Total Amount: " + totalAmount);
        model.addAttribute("selectedItems", lCartItems);
        model.addAttribute("totalAmount", totalAmount); // Thêm tổng tiền vào mô hình
        Users lCartItems = (Users) session.getAttribute("taikhoan");
        if (lCartItems != null) {
            model.addAttribute("taikhoan", lCartItems);
            System.out.println(lCartItems.toString());
        }
        return "payment";
    }

    @SuppressWarnings("unchecked")
    @PostMapping("checkpayment")
    public ResponseEntity<String> postMethodName(@RequestBody Map<String, Object> requestData, HttpSession session,
            HttpServletRequest request) {
        List<CartItem> lCartItems = (List<CartItem>) session.getAttribute("selectedItems");
        session.setAttribute("selectedItems", lCartItems);
        Users user = (Users) session.getAttribute("taikhoan");

        if (user == null) {
            System.out.println("USernulll\n\n\n\n\n\n\n");
        } else {
            session.setAttribute("taikhoan", user);
            System.out.println(user.toString());
        }
        System.out.println("Sản phẩm thêm vào giỏ hàng: " + lCartItems.toString());

        session.setAttribute("dataRequest", requestData);
        System.out.println("RequestDataCheckPayMent: " + requestData.toString());

        String method = requestData.get("method") != null ? (String) requestData.get("method") : "";
        System.out.println(method);
        if (method.equals("cod")) {
            return ResponseEntity.ok("/payment/thanksyou");
        }
        double total = lCartItems.stream().mapToDouble(
                item -> ((item.getItem().getPrice()
                        - (item.getItem().getPrice() * (item.getItem().getDiscount() / 100.0)))
                        * item.getQuantity()))
                .sum();
        int id = Integer.parseInt(orderDAO.getLatestOrderId()); // Lấy ID đơn hàng mới nhất
        id++;
        String orderInfo = "Thanh toan hoa don " + String.valueOf(id);
        String urlReturn = "http://localhost:8080/payment/thanksyou";
        String vnpayUrl = vnPayService.createOrder(request, (int) total, orderInfo, urlReturn);

        return ResponseEntity.ok(vnpayUrl); // Redirect to the payment page after processing
    }

    @SuppressWarnings("unchecked")
    @GetMapping("/thanksyou")
    public String GetPageThanks(HttpSession session, Model model, RedirectAttributes redirectAttributes,
            HttpServletRequest request) {
        System.out.println("Cảm ơn vì đã mua hàng "
                + DateFormat.getDateTimeInstance(0, 0, Locale.getDefault()).format(new Date()));
        Map<String, Object> requestData = (Map<String, Object>) session.getAttribute("dataRequest");
        if (requestData == null) {
            redirectAttributes.addFlashAttribute("alertMessage", "Đã sảy ra lỗi ");
            return "redirect:/alert-redirect";
        }
        String l = requestData.get("ten") != null ? (String) requestData.get("ten") : "";
        String l1 = requestData.get("sdt") != null ? (String) requestData.get("sdt") : "";
        String l2 = requestData.get("diaChi") != null ? (String) requestData.get("diaChi") : "";
        String l3 = requestData.get("method") != null ? (String) requestData.get("method") : "";
        System.out.println("RequestData ThanksYou: " + requestData.toString());
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
            int status = userDAO.createUser(user);
            if (status != 1) {
                System.out.println("\\n" + //
                        "\\n" + //
                        "ERRRRRRRRRRRRRRRRRRRRRROOOOOOOOOOO\n\n");
            } else {
                System.out.println("Đăng ký thành công User" + user.toString());
            }
        }
        lCartItems = (List<CartItem>) session.getAttribute("selectedItems"); // Giả lập danh sách sản phẩm
        double totalAmount = lCartItems.stream().mapToDouble(
                item -> ((item.getItem().getPrice()
                        - (item.getItem().getPrice() * (item.getItem().getDiscount() / 100.0)))
                        * item.getQuantity()))
                .sum();// Tính tổng tiền
        String payments = l3.equals("payment") ? "VNPAY" : "COD";
        String notification = !l3.equals("payments") ? "Thanh toán khi giao hàng"
                : "Đơn hàng đã được thanh toán trước!!!";
        int id = Integer.parseInt(orderDAO.getLatestOrderId()); // Lấy ID đơn hàng mới nhất
        id++;
        Order nOrder = new Order(String.valueOf(id),
                user.getId(),
                timestampString,
                l + "{/}" + l1 + "{/}" + l2 + "{/}" + timestampString,
                payments,
                "Pending",
                totalAmount, lCartItems);
        if (orderDAO.createOrder(nOrder) == 1) // Tạo đơn hàng mới
        {
            System.out.println("Tạo đơn hàng thành công!");
            if (orderDAO.addOrderDetails(String.valueOf(id), lCartItems) == 1) // Thêm sản phẩm vào đơn hàng
            {
                for (CartItem cartItem : lCartItems) {
                    if (productDAO.updateQuantityPro(cartItem.getItem(), cartItem.getQuantity()) == 1) {
                        System.out.println("Cap nhat lai thanh cong Pro: " + cartItem.getIDItem());
                    }
                }
                System.out.println("Thêm sản phẩm vào đơn hàng thành công!");
                // orderDAO.addtoPayment(order
            } else {
                System.out.println("Thêm sản phẩm vào đơn hàng thất bại!");
            }
        } else {
            System.out.println("Tạo đơn hàng thất bại!");
        }

        List<CartItem> listitem = (List<CartItem>) session.getAttribute("listcart");

        // Duyệt qua từng item trong listSelect
        for (CartItem cartItem2 : lCartItems) {
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

        model.addAttribute("listcart", lCartItems);
        model.addAttribute("userss", user);
        model.addAttribute("idorder", id);
        model.addAttribute("total", totalAmount);
        model.addAttribute("notification", notification);
        session.removeAttribute("selectedItems");
        return "thanksyou";
    }

}
