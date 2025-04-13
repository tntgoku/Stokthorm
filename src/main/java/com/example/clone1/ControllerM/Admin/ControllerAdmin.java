package com.example.clone1.ControllerM.Admin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.example.clone1.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.clone1.DAO.CategoryDAO;
import com.example.clone1.DAO.OrderDAO;
import com.example.clone1.DAO.ProductDAO;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ControllerAdmin {

    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private CategoryDAO categoryDAO;
    private static final String UPLOAD_DIR = "./src/main/resources/static/assets/img/logo/";

    @GetMapping("/admin")
    public String gethomepageadmin(Model model) {
        System.out.println("Welecome Go to Home Page View Admin");
        return "admin/homepage";
    }

    @GetMapping("/info-Acc")
    public String getinfome(@RequestParam("id") String UsersID, Model model, HttpSession session) {
        if (UsersID == null) {
            return "redirect:/";
        }
        Users users = (Users) session.getAttribute("taikhoan");
        if (users == null) {
            return "redirect:/";
        }
        model.addAttribute("taikhoan", users);
        return "infome";
    }

    @PostMapping("/change")
    public String changeProfile(
            @RequestParam("tk_id") String id,
            @RequestParam("ho_ten") String name,
            @RequestParam("email") String email,
            @RequestParam("mat_khau") String password,
            @RequestParam("sdt") String phone,
            @RequestParam("dia_chi") String address) {
        // Xử lý cập nhật thông tin người dùng ở đây, ví dụ:
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phone);
        System.out.println("Address: " + address);

        // redirect lại trang thông tin người dùng
        return "redirect:/info-Acc?id=" + id;
    }

    @GetMapping("/admin/listOrder")
    public String getlistOrder(Model model, HttpSession session,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "act", required = false) String act,
            @RequestParam(value = "id", required = false) String orderId) {
        if ("detailorder".equals(act) && orderId != null) {
            Order order = orderDAO.getOrderById(orderId);
            if (order != null) {
                order.setCartItems(orderDAO.getCartItemsByOrderId(orderId));
                String arr[] = order.getNote().split("\\{\\/\\}");
                order.setName(arr[0]);
                order.setPhone(arr[1]);
                order.setAddress(arr[2]);
                order.setDate(arr[3]);
                for (CartItem item : order.getCartItems()) {
                    Product temp = productDAO.getProduct(item.getIDItem1());
                    if (temp != null) {
                        item.setItem(temp);
                        item.getItem().setListIMG(productDAO.getListIMG(temp));
                    }
                }
                model.addAttribute("order", order);
                model.addAttribute("productDonHang", order.getCartItems());
                return "admin/order/orderdetail"; // Tên file HTML trang chi tiết
            }

            else {
                model.addAttribute("error", "Không tìm thấy đơn hàng");
            }
        }

        List<Order> listorder = orderDAO.getAllOrders(page, size);
        int totalOrders = orderDAO.countTotalOrders();
        int totalPages = -1;
        if (totalOrders > 0) {

            totalPages = (int) Math.ceil((double) totalOrders / size);
            for (Order order : listorder) {
                order.setCartItems(orderDAO.getCartItemsByOrderId(order.getOrderId()));
                String arr[] = order.getNote().split("\\{\\/\\}");
                if (arr.length >= 4) {
                    order.setName(arr[0]);
                    order.setPhone(arr[1]);
                    order.setAddress(arr[2]);
                    order.setDate(arr[3]);
                } else if (arr.length == 3) {

                } else {
                    // Xử lý khi không đủ phần tử trong mảng arr
                    order.setName(arr.length > 0 ? arr[0] : ""); // Gán giá trị mặc định nếu không có phần tử
                    order.setPhone(arr.length > 1 ? arr[1] : "");
                    order.setAddress(arr.length > 2 ? arr[2] : "");
                    order.setDate(arr.length > 3 ? arr[3] : "");
                }

                for (CartItem item : order.getCartItems()) {
                    Product temp = productDAO.getProduct(item.getIDItem1()) != null
                            ? productDAO.getProduct(item.getIDItem1())
                            : null;
                    if (temp != null) {
                        item.setItem(temp);
                        item.getItem().setListIMG(productDAO.getListIMG(temp));
                    }
                }

            }
        }
        model.addAttribute("listOrder", listorder);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        return "admin/order/listorder";
    }

    @PostMapping("/admin/listOrder")
    public ResponseEntity<?> PostUpdateOrder(@RequestParam("ID") int orderId,
            @RequestParam("statusorder") String status) {
        if (orderDAO.updateStatusOrder(status, orderId) == -1) {
            ResponseEntity.badRequest().body("Update failed");
        }
        System.out.println(status + "\n\n\n" + orderId);
        return ResponseEntity.ok("Update succesful");
    }

    @GetMapping("/admin/listproduct")
    public String FormList(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            HttpSession session,
            Model model) {

        List<Product> listProductC = productDAO.getAllProduct();
        for (Product map : listProductC) {
            map.setListIMG(productDAO.getListIMG(map));
            if (map.getListIMG() == null)
                System.out.println("Ko co anh!!!!!!!!!!!!!!!!!!1");
        }
        model.addAttribute("producta", productDAO.getAllProduct());
        return "admin/products/listproduct"; // Hoặc trả về một trang khác
    }

    @GetMapping("/admin/listproduct/act/formAddProduct")
    public String getMethodName(Model model, HttpSession session) {
        model.addAttribute("categories", categoryDAO.getallCate());
        return "admin/products/addproduct";
    }

    @PostMapping("/admin/listproduct/act/formAddProduct")
    public String admin(
            @RequestParam("Name") String name,
            @RequestParam("Price") double price,
            @RequestParam("Description") String description,
            @RequestParam("Size") String size,
            @RequestParam("Color") String color,
            @RequestParam("Img") MultipartFile imgFile,
            @RequestParam("listIMG") MultipartFile[] listImgFiles,
            @RequestParam("CategoryID") String categoryId,
            @RequestParam("Quantity") int quantity,
            @RequestParam("Discount") double discount,
            Model model) {
        Product temp = new Product();
        int number = productDAO.top1Product();
        number = number + 1;
        int giatri = Integer.parseInt(categoryId);
        System.out.println(giatri);
        temp.setCategoryID(giatri);
        System.out.println("Value: " + number);
        temp.setID(String.valueOf(number));
        temp.setName(name);
        temp.setColor(color);
        temp.setSize(size);
        temp.setDiscount((float) discount);
        temp.setDescription(description);
        temp.setPrice((float) price);
        temp.setQuantity(quantity);
        // Log thông tin ảnh chính
        String imString = "";
        if (!imgFile.isEmpty()) {
            imString = Fileimg(imgFile);
            temp.setImg(imString);
        } else {
            System.out.println("Error");
        }

        if (listImgFiles != null) {
            List<String> listimg = new ArrayList<String>();
            for (MultipartFile multipartFile : listImgFiles) {
                if (!multipartFile.isEmpty()) {
                    System.out.println(Fileimg(multipartFile) + "\nTheem thanh cong");

                    listimg.add(Fileimg(imgFile));
                }
            }
            temp.setListIMG(listimg);
        }
        int status = productDAO.addProduct(temp);
        if (status != -1) {
            model.addAttribute("message", "Thêm Thành công ");
        } else {
            model.addAttribute("message", "Có lỗi sảy ra !!!!!");
        }
        model.addAttribute("categories", categoryDAO.getallCate());
        return "admin/products/addproduct"; // Chuyển hướng về danh sách sản phẩm
    }

    public String Fileimg(MultipartFile fileimg) {
        String urlss;

        String originalFilename = fileimg.getOriginalFilename();
        if (originalFilename == null || originalFilename.isEmpty()) {
            return "";
        }

        String baseName = originalFilename.substring(0, originalFilename.lastIndexOf('.'));
        String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));

        // Tạo tên file mới nếu file đã tồn tại
        File destinationFile = new File(UPLOAD_DIR + File.separator + originalFilename);
        int index = 1;
        while (destinationFile.exists()) {
            String newFileName = baseName + "_" + index + extension;
            destinationFile = new File(UPLOAD_DIR + File.separator + newFileName);
            index++;
        }

        // Cập nhật đường dẫn thực tế
        urlss = destinationFile.getPath();

        try (InputStream inputStream = fileimg.getInputStream();
                OutputStream outputStream = new FileOutputStream(destinationFile)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            // Print info
            System.out.println("File uploaded: " + destinationFile.getName());
            System.out.println("File size: " + fileimg.getSize());
            System.out.println("Content type: " + fileimg.getContentType());

            // Trả về đường dẫn đã upload (nếu cần chuyển thành URL thì xử lý thêm)
            return urlss.replace(".\\src\\main\\resources\\static\\assets\\img\\logo\\", "/assets/img/logo/");

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @GetMapping("/admin/listproduct/formEditProduct")
    public String showEditForm(@RequestParam("id") Long id, Model model) {
        Product temp = productDAO.FindProduct1(String.valueOf(id));
        temp.setListIMG(productDAO.getListIMG(temp));
        model.addAttribute("product", temp);
        model.addAttribute("listCategory", categoryDAO.getallCate());
        return "admin/products/editproduct";
    }

    @PostMapping("/admin/listproduct/xoaProduct")
    public String deleteProduct(@RequestParam("id") int id, Model model, HttpSession session) {
        // Gọi stored procedure hoặc DAO để xoá
        if (productDAO.deleteProduct(id) != -1) {
            model.addAttribute("succ", "Xoá thành công!!!  " + id);
        } else {
            model.addAttribute("succ", "Xoá không thành công.....  " + id);
        }
        return FormList(0, 10, session, model); // Quay về danh sách sản phẩm
    }
}