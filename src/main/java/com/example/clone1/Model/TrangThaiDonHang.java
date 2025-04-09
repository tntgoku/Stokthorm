package com.example.clone1.Model;

public enum TrangThaiDonHang {
    PENDING("Chờ xác nhận"),
    SHIPPED("Đang giao"),
    DELIVERED("Đã giao"),
    CANCELLED("Hủy đơn"),
    UNKNOWN("Không xác định");

    private final String moTa;

    TrangThaiDonHang(String moTa) {
        this.moTa = moTa;
    }

    public String getMoTa() {
        return moTa;
    }

    public static TrangThaiDonHang fromString(String status) {
        try {
            return TrangThaiDonHang.valueOf(status.toUpperCase());
        } catch (Exception e) {
            return UNKNOWN;
        }
    }
}
