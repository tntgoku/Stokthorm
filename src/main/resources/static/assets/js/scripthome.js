$(document).ready(function() {
    $("#loginForm").submit(function(event) {
        event.preventDefault(); // Ngăn chặn hành vi mặc định của form

        var email = $("input[name='email']").val();
        var password = $("input[name='mat_khau']").val();
        var remember = $("input[name='rememberMe']").is(":checked");
        if (email == "" || password == "") {
            // $(".text-danger").html("<h3 style='color: red;'>Vui lòng nhập đầy đủ thông tin!</h3>");
            alert("Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        // Kiểm tra định dạng email
        var emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        if (!emailPattern.test(email)) {
            $(".text-danger").html("<h3 style='color: red;'>Địa chỉ email không hợp lệ!</h3>");
            return;
        }

        $.ajax({
            type: "POST",
            url: "/login/check-login",
            contentType: "application/json",
            data: JSON.stringify({
                email: email,
                mat_khau: password,
                rememberMe: remember
            }),
            success: function(response) {
                console.log(response.success);
                if (response.success == true) {
                    alert(response.message);
                    window.location.href = response.homepage; // Chuyển hướng khi đăng nhập thành công
                } else {
                    $("#errorMessage").html("<h3 style='color: red;'>" + response.message + "</h3>");
                }
            },
            error: function(xhr) {
                $(".text-danger").html("<h3 style='color: red;'>" + xhr.responseJSON.message + "</h3>");
            }
        });
    });
});



$(".form-selectcategori option").on("change", function() {
    const selectedValue = $(this).val(); // Lấy giá trị (value) của option được chọn
    const selectedText = $(this).find("option:selected").text(); // Lấy text hiển thị
    if ($(this).val() === selectedValue) {
        $(this).prop("selected", true);
    } else {
        $(this).prop("selected", false);
    }
    console.log("Giá trị được chọn (value):", selectedValue);
    console.log("Tên hiển thị (text):", selectedText);
});

// Hàm xác nhận đăng xuất
function confirmLogout(event) {
    event.preventDefault(); // Ngăn chặn hành vi mặc định của thẻ

    if (confirm("Bạn muốn đăng xuất?")) {
        $.ajax({
            type: "GET",
            url: "/logout",
            contentType: "application/json",
        });
    }

    $('#Img').on('change', function() {
        var file = this.files[0];
        if (file && file.type.startsWith('image/')) {
            var reader = new FileReader();
            reader.onload = function(e) {
                $('#previewImg').attr('src', e.target.result);
            }
            reader.readAsDataURL(file);
        } else {
            // Nếu không phải ảnh, xóa src của thẻ img
            $('#previewImg').attr('src', '');
        }
    });

}


$(document).ready(function() {
    $('.status-select').on('change', function() {
        const id = $(this).data('id'); // lấy data-id
        const newStatus = $(this).val(); // lấy giá trị mới


        console.log(`Đã chọn trạng thái mới: ${newStatus} cho đơn hàng có ID: ${id}`);
        $.ajax({
            type: "POST",
            url: "/admin/listOrder",
            data: {
                ID: id,
                statusorder: newStatus
            },
            success: function(response) {
                alert("Cập nhật lại thành công Order: " + id + ", Status: " + newStatus);
            },
            error: function(xhr, status, error) {
                console.error("❌ Lỗi khi gửi request:", error);
                alert("Cập nhật trạng thái thất bại!");
            }
        });
    });
});

$(document).ready(function() {
    $(".btn-remove").click(function(e) {
        e.preventDefault();
        if (!confirm("Bạn có chắc chắn xóa hay không?")) return;
        const id = $(this).data("id");
        $.ajax({
            type: "POST",
            url: "/admin/listproduct/xoaProduct",
            data: { id: id },
            success: function(response) {
                alert("Xóa thành công sản phẩm !!!!!   " + id);
                location.reload();
            },
            error: function(xhr) {
                alert("Xoá thất bại!");
            }
        });
    });
});