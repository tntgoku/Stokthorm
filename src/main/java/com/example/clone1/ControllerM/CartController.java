package com.example.clone1.ControllerM;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.clone1.DAO.*;
import com.example.clone1.Model.*;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private ProductDAO productDAO;

    private List<CartItem> lCartItems;

    @SuppressWarnings("unchecked")
    @GetMapping
    public String viewCart(Model model, HttpSession session) {

        lCartItems = (List<CartItem>) session.getAttribute("listcart"); // Giả lập danh sách sản phẩm
        if (lCartItems == null) {
            lCartItems = new ArrayList<>();
        }
        model.addAttribute("listcart", lCartItems);
        double tongTatCaTien = lCartItems.stream().mapToDouble(CartItem::getGiasp).sum();
        model.addAttribute("tongTatCaTien", tongTatCaTien);
        System.out.println("Tong tat ca tien: " + tongTatCaTien);
        return "cart";
    }

    @SuppressWarnings("unchecked")
    @PostMapping("/add")
    public ResponseEntity<?> addtocart(@RequestBody Map<String, Object> requestData, HttpSession session) {
        System.out.println("Sản phẩm thêm vào giỏ hàng: " + requestData);

        String spbtId = (String) requestData.get("spbt_id");
        String quantity = (String) requestData.get("so_luong");
        String stock = (String) requestData.get("stock");

        Product producttemp = productDAO.FindProduct1(spbtId);
        if (producttemp == null)
            return ResponseEntity.badRequest().body("Sản phẩm không tồn tại!");
        producttemp.setListIMG(productDAO.getListIMG(producttemp));
        System.out.println("List ảnh: " + producttemp.getListIMG().toString());
        System.out.println("Sản phẩm thêm vào giỏ hàng: " + producttemp.toString());
        if (Integer.parseInt(stock) < Integer.parseInt(quantity))
            return ResponseEntity.badRequest().body("Số lượng sản phẩm không đủ!");

        lCartItems = (List<CartItem>) session.getAttribute("listcart");
        if (lCartItems == null) {
            lCartItems = new ArrayList<>();
        }

        for (CartItem item : lCartItems) {
            if (item.getIDItem().equals(spbtId)) {
                item.setQuantity(item.getQuantity() + Integer.parseInt(quantity));
                item.setGiasp(item.getQuantity()
                        * (producttemp.getPrice() - (producttemp.getPrice() * (producttemp.getDiscount() / 100))));
                session.setAttribute("listcart", lCartItems);
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
        lCartItems.add(newItem);
        session.setAttribute("listcart", lCartItems);

        return ResponseEntity.ok("Sản phẩm đã thêm thành công!");
    }

    @SuppressWarnings("unchecked")
    @PostMapping("/delete")
    public ResponseEntity<?> deleteItem(@RequestBody Map<String, Object> requestData, HttpSession session) {
        System.out.println("Sản phẩm xóa khỏi giỏ hàng: " + requestData);

        String spbtId = (String) requestData.get("spbt_id");
        lCartItems = (List<CartItem>) session.getAttribute("listcart");

        if (lCartItems != null) {
            lCartItems.removeIf(item -> item.getIDItem().equals(spbtId));
            session.setAttribute("listcart", lCartItems);
        }

        return ResponseEntity.ok("Sản phẩm đã xóa thành công!");
    }

    @SuppressWarnings("unchecked")
    @PostMapping("/checkout")
    public ResponseEntity<String> checkout(HttpSession session,
            @RequestBody Map<String, List<Map<String, Object>>> requestData) {
        List<Map<String, Object>> request = requestData.get("products");
        System.out.println("Sản phẩm được chọn để thanh toán: " + requestData.toString());

        List<CartItem> lCartItemsselect = new ArrayList<>();
        for (Map<String, Object> item : request) {
            String id = item.get("id") != null ? item.get("id").toString() : "";
            String quantity = item.get("quantity") != null ? item.get("quantity").toString() : "";

            Product temps = productDAO.FindProduct1(id);
            temps.setListIMG(productDAO.getListIMG(temps));
            lCartItemsselect.add(new CartItem(id, Integer.parseInt(quantity), temps));
        }

        // Giả lập danh sách sản phẩm đã chọn để thanh toán
        lCartItems = (List<CartItem>) session.getAttribute("listcart");

        if (lCartItems == null || lCartItems.isEmpty()) {
            return ResponseEntity.ok("/cart"); // Trả về đường dẫn /cart nếu giỏ hàng trống
        }
        session.setAttribute("selectedItems", lCartItemsselect);
        return ResponseEntity.ok("/payment"); // Trả về đường dẫn /payment
    }

}
