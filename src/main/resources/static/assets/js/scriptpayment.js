$(document).ready(function() {
    // Hàm xử lý khi nhấn nút "Thanh toán"
    $('#checkout-form').submit(function(e) {
        e.preventDefault();

        // Kiểm tra xem có checkbox nào được chọn không
        const selectedProducts = $('.select-product:checked');

        if (selectedProducts.length === 0) {
            // Nếu không có sản phẩm nào được chọn, hiển thị thông báo cảnh báo
            alert("Bạn chưa chọn sản phẩm để thanh toán.");
            return;
        }

        // Nếu có sản phẩm được chọn, thực hiện AJAX gửi dữ liệu
        const selectedProductIds = [];
        selectedProducts.each(function() {
            selectedProductIds.push($(this).val());
        });

        // Gửi AJAX
        $.ajax({
            url: '/cart/checkout', // Đường dẫn để xử lý thanh toán
            type: 'POST',
            contentType: 'application/json', // Định dạng dữ liệu gửi đi
            data: JSON.stringify({ productIds: selectedProductIds }), // Gửi danh sách sản phẩm được chọn
            success: function(response) {
                // Kiểm tra phản hồi từ server (ví dụ: thanh toán thành công)
                if (response.status === "success") {
                    // Nếu thanh toán thành công, chuyển hướng người dùng đến trang cảm ơn
                    window.location.href = '/payment'; // Hoặc chuyển hướng về trang bạn muốn
                } else {
                    // Nếu có lỗi, hiển thị thông báo lỗi
                    alert(response.message || "Có lỗi xảy ra, vui lòng thử lại.");
                }
            },
            error: function(xhr, status, error) {
                // Nếu có lỗi trong quá trình gửi yêu cầu
                alert("Lỗi kết nối: " + error);
            }
        });
    });
});