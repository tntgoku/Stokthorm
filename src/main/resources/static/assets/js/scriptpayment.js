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
        const selectedItems = [];
        // Nếu có sản phẩm được chọn, thực hiện AJAX gửi dữ liệu
        selectedProducts.each(function() {
            const id = $(this).val();
            const quantity = $(this).data('quantity');
            const price = $(this).data('price');
            const discount = $(this).data('discount');

            selectedItems.push({
                id: id,
                quantity: quantity,
                price: price,
                discount: discount
            });
        });

        // Gửi AJAX
        $.ajax({
            url: '/cart/checkout', // Đường dẫn để xử lý thanh toán
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ products: selectedItems }), // Gửi danh sách sản phẩm được chọn
            success: function(response) {
                console.log(response); // In ra phản hồi để kiểm tra

                // Kiểm tra nếu phản hồi là URL cần chuyển hướng
                if (response === "/payment") {
                    window.location.href = response; // Chuyển hướng đến trang thanh toán
                } else if (response === "/cart") {
                    window.location.href = response; // Chuyển hướng về giỏ hàng nếu giỏ trống
                } else if (response === "/thanksyou") {
                    window.location.href = response;
                } else {
                    alert("Có lỗi xảy ra, vui lòng thử lại.");
                }
            },
            error: function(xhr, status, error) {
                // Nếu có lỗi trong quá trình gửi yêu cầu
                alert("Lỗi kết nối: " + error);
            }
        });

    });

    $('#checkpayment').submit(function(e) {
        e.preventDefault();
        var ten, sdt, diaChi;
        let selectedMethod = $('input[name="paymentmethod"]:checked').val();

        $.ajax({
            type: "POST",
            url: "/payment/checkpayment",
            contentType: "application/json", // 🟢 gửi dưới dạng JSON
            data: JSON.stringify({
                ten: ten,
                sdt: sdt,
                diaChi: diaChi,
                method: selectedMethod // key này phải là "method" để khớp với backend
            }),
            dataType: "text", // vì backend trả về chuỗi "/thanksyou" hoặc "/payment"
            success: function(response) {
                console.log("Phản hồi từ server:", response);
                if (response === "/payment/thanksyou") {
                    window.location.href = response;
                } else {

                }
            },
            error: function(xhr, status, error) {
                alert("Đã xảy ra lỗi");
                console.error("Đã xảy ra lỗi:", error);
            }
        });
    });
    // $("#ship_to_different").change(function() {
    //     if ($(this).is(":checked")) {
    //         console.log("Checkbox đã được bật");
    //         console.log("Checkbox đã được bật (checked)");
    //         ten = $('input[name="ten_nhan"]').val();
    //         sdt = $('input[name="sdt_nhan"]').val();
    //         diaChi = $('input[name="dia_chi_nhan"]').val();
    //         console.log(ten + ", " + sdt + ", " + diaChi);
    //         // Thêm hành động khi bật
    //     } else {
    //         console.log("Checkbox đã được tắt");
    //         console.log("Checkbox đã được tắt (unchecked)");
    //         ten = $('input[name="ho_ten"]').val();
    //         sdt = $('input[name="sdt"]').val();
    //         diaChi = $('input[name="dia_chi"]').val();
    //         // Thêm hành động khi tắt
    //         console.log(ten + ", " + sdt + ", " + diaChi);
    //     }
    // });
    $('#ship_to_different').change(function() {
        if ($(this).is(':checked')) {
            alert('Bạn đã chọn gửi đến địa chỉ khác!');
            // $('#another-address').show();
        } else {
            alert('Bạn đã bỏ chọn gửi đến địa chỉ khác.');
            // $('#another-address').hide();
        }
    });
});