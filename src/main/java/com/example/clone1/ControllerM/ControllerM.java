package com.example.clone1.ControllerM;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.clone1.DAO.ProductDAO;
import com.example.clone1.Model.Product;

import jakarta.servlet.http.HttpSession;

@Controller
public class ControllerM {

    @Autowired
    private ProductDAO productD;
    List<Product> listProductC;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    String timestampString;

    @GetMapping("/")
    public String GoHomePage(Model model) {
        System.out.println("Vao trang chu");
        return HomePage(model);
    }

    @GetMapping("/home")
    public String HomePage(Model model) {

        listProductC = productD.getAllProduct();
        if (!listProductC.isEmpty()) {
            for (Product map : listProductC) {
                map.setListIMG(productD.getListIMG(map));
                if (map.getListIMG() == null)
                    System.out.println("Ko co anh!!!!!!!!!!!!!!!!!!1");
                else
                    System.out.println("Co anh: " + map.getListIMG().toString());
                System.out.println("Data: " + map.toString());

            }
            model.addAttribute("producta", listProductC);
        } else {
            System.out.println("Error");
        }

        timestampString = sdf.format(new Date()); // Lấy thời gian hiện tại dưới dạng String

        System.out.println("Timestamp String: " + timestampString);
        return "index";
    }

    @GetMapping("login")
    public String LoginPage(Model model) {
        return "login";
    }

    // -------------------LOGOUT
    @GetMapping("logout")
    public String Logout(HttpSession session, Model model) {
        System.out.println("Nguoi dung yeu cau Logout");
        session.removeAttribute("taikhoan");
        ; // Xóa toàn bộ session
        return GoHomePage(model);
    }

    @GetMapping("/detailproduct")
    public String DetailProductPage(@RequestParam(value = "atc", required = false) String atc,
            @RequestParam("id") String productId,
            @RequestParam("size_id") String storage,
            @RequestParam("sp_id") int spId,
            Model model) {
        // ✅ Xử lý logic: Lấy thông tin sản phẩm từ database
        // Map<String, Object> product = productD.FindProduct(productId);
        Product product = productD.FindProduct1(productId);
        if (product == null) {
            System.out.println("Khong co san pham");
            return "/";
        }
        List<String> images = productD.getListIMG(product);
        if (images != null) {
            product.setListIMG(images);
            System.out.println("ListIMG: " + images);
        } else {
            images = new ArrayList<>(); // Tránh lỗi NullPointerException
            System.out.println("ListIMG: Không có hình ảnh nào!");
        }

        // Load Size,Quantity,Color

        List<String> listSize = productD.getListSize(product);
        List<String> listColor = productD.getListColor(product);
        List<String> listQuantity = productD.getListQuantity(product);
        if (listSize == null || listColor == null || listQuantity == null) {
            System.out.println("Khong co size, color, quantity");
        }
        // ✅ Đưa dữ liệu vào Model để hiển thị trên giao diện
        model.addAttribute("product", product);
        model.addAttribute("listSize", listSize);
        model.addAttribute("listColor", listColor);
        model.addAttribute("listQuantity", listQuantity);
        listProductC = productD.getAllProductByCate(product.getCategoryID());
        if (listProductC == null)
            System.out.println("Khong co san pham");
        else
            System.out.println("Co san pham");
        for (Product map : listProductC) {
            System.out.println("Data: " + map.toString());
            List<String> listimg = productD.getListIMG(map);
            if (listimg.isEmpty())
                System.out.println("Ko co anh!!!!!!!!!!!!!!!!!!1");
            map.setListIMG(productD.getListIMG(map));
        }
        model.addAttribute("listProductC", listProductC);
        return "detail";
    }

    @GetMapping("/alert-redirect")
    public String alertRedirect() {
        return "alertredirect"; // view trung gian
    }
}