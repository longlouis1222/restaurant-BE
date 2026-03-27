package vn.ptit.restaurant.dto.response;

import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HoaDonResponse {

    private String maHoaDon;
    private BigDecimal tongTien;
    private String trangThai;

    // Thông tin khách hàng
    private String tenKhachHang;
    private String soDienThoai;

    private List<ChiTietResponse> chiTietList;

    @Data
    @Builder
    public static class ChiTietResponse {
        private String maMon;
        private String tenMon;
        private Integer soLuong;
        private BigDecimal donGia;
        private BigDecimal thanhTien;
    }
}