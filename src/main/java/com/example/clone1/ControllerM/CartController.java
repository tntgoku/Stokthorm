package com.example.clone1.ControllerM;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.clone1.DAO.*;
import com.example.clone1.Model.*;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private ProductDAO productDAO;
    @Autowired
    private UserDAO usersDAO;

    @GetMapping
    public String viewCart(Model model, HttpSession session) {

        List<CartItem> cartItems = (List<CartItem>) session.getAttribute("listcart"); // Giả lập danh sách sản phẩm
        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }
        model.addAttribute("listcart", cartItems);
        double tongTatCaTien = cartItems.stream().mapToDouble(CartItem::getGiasp).sum();
        model.addAttribute("tongTatCaTien", tongTatCaTien);
        System.out.println("Tong tat ca tien: " + tongTatCaTien);
        return "cart";
    }

    @PostMapping("/add")
    public ResponseEntity<?> addtocart(@RequestBody Map<String, Object> requestData, HttpSession session) {
        System.out.println("Sản phẩm thêm vào giỏ hàng: " + requestData);

        String spbtId = (String) requestData.get("spbt_id");
        String sizeId = (String) requestData.get("size_id");
        String quantity = (String) requestData.get("so_luong");
        String price = (String) requestData.get("price");
        String stock = (String) requestData.get("stock");

        Product producttemp = productDAO.FindProduct1(spbtId);
        if (producttemp == null)
            return ResponseEntity.badRequest().body("Sản phẩm không tồn tại!");
        producttemp.setListIMG(productDAO.getListIMG(producttemp));
        System.out.println("List ảnh: " + producttemp.getListIMG().toString());
        System.out.println("Sản phẩm thêm vào giỏ hàng: " + producttemp.toString());
        if (Integer.parseInt(stock) < Integer.parseInt(quantity))
            return ResponseEntity.badRequest().body("Số lượng sản phẩm không đủ!");

        List<CartItem> listitem = (List<CartItem>) session.getAttribute("listcart");
        if (listitem == null) {
            listitem = new ArrayList<>();
        }

        Users account = (Users) session.getAttribute("taikhoan");
        String idaccount = (account == null) ? "-1" : account.getId();
        for (CartItem item : listitem) {
            if (item.getIDItem().equals(spbtId)) {
                item.setQuantity(item.getQuantity() + Integer.parseInt(quantity));
                item.setGiasp(item.getQuantity()
                        * (producttemp.getPrice() - (producttemp.getPrice() * (producttemp.getDiscount() / 100))));
                session.setAttribute("listcart", listitem);
                return ResponseEntity.ok("Sản phẩm đã thêm thành công!");
            }
        }

        // Thêm mới nếu chưa có trong giỏ
        CartItem newItem = new CartItem();
        newItem.setIDItem(spbtId);
        newItem.setQuantity(Integer.parseInt(quantity));
        newItem.setItem(producttemp);
        newItem.setGiasp(newItem.getQuantity()
                * (producttemp.getPrice() - (producttemp.getPrice() * (producttemp.getDiscount() / 100))));
        listitem.add(newItem);
        session.setAttribute("listcart", listitem);

        return ResponseEntity.ok("Sản phẩm đã thêm thành công!");
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteItem(@RequestBody Map<String, Object> requestData, HttpSession session) {
        System.out.println("Sản phẩm xóa khỏi giỏ hàng: " + requestData);

        String spbtId = (String) requestData.get("spbt_id");
        List<CartItem> listitem = (List<CartItem>) session.getAttribute("listcart");

        if (listitem != null) {
            listitem.removeIf(item -> item.getIDItem().equals(spbtId));
            session.setAttribute("listcart", listitem);
        }

        return ResponseEntity.ok("Sản phẩm đã xóa thành công!");
    }

    @PostMapping("/checkout")
    public String checkout(
            HttpSession session, Model model) {

        List<CartItem> listitem = (List<CartItem>) session.getAttribute("listcart");
        if (listitem == null || listitem.isEmpty()) {
            model.addAttribute("error", "Giỏ hàng trống!");
            return "redirect:/cart";
        }

        // Xử lý thanh toán ở đây (lưu vào database, gửi email, v.v.)
        // Sau khi thanh toán thành công, xóa giỏ hàng
        session.removeAttribute("listcart");

        return "redirect:/payment";
    }
}
