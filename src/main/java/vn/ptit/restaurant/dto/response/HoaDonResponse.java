package vn.ptit.restaurant.dto.response;

import lombok.*;
        import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class HoaDonResponse {

    private String id;
    private String maHoaDon;
    private BigDecimal tongTien;
    private String trangThai;
    private List<ChiTietResponse> chiTietList;

    @Data
    @Builder
    public static class ChiTietResponse {
        private String tenMon;
        private Integer soLuong;
        private BigDecimal donGia;
        private BigDecimal thanhTien;
    }
}