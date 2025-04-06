$(document).ready(function() {
    $("#add-to-cart-btn").on("click", function(e) {
        e.preventDefault(); // Ngăn chặn hành động mặc định của form

        // Tạo đối tượng sản phẩm
        var spbtId = $(".selected-spbt-id").val();
        var sizeId = $(".selected-size-id").val();
        var quantity = $("#quantity-input").val();
        var price = $("#selected-price").text();
        var stock = $("#selected-stock").text();

        // Tạo đối tượng sản phẩm mới
        var newProduct = {
            spbt_id: spbtId,
            size_id: sizeId,
            so_luong: quantity,
            price: price,
            stock: stock
        };

        $.ajax({
            url: "/cart/add", // Địa chỉ gửi dữ liệu
            type: "POST", // Phương thức HTTP
            contentType: 'application/json', // Đảm bảo gửi dữ liệu dạng JSON
            data: JSON.stringify(newProduct), // Chuyển mảng thành chuỗi JSON
            success: function(response) {
                // Xử lý khi nhận được phản hồi thành công từ server
                console.log(response);
                // alert('Sản phẩm đã được thêm vào giỏ hàng!');
            },
            error: function(xhr, status, error) {
                // Xử lý khi có lỗi xảy ra trong quá trình gửi dữ liệu
                console.error("Lỗi:", error);
                alert('Có lỗi xảy ra, vui lòng thử lại!');
            }
        });
    });
});