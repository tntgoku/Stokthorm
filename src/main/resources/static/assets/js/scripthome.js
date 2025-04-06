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
}