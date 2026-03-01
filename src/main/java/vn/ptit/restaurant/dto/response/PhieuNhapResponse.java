package vn.ptit.restaurant.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhieuNhapResponse {
    private String maPhieuNhap;
    private String maNcc;
    private String maNhanVien;
    private LocalDateTime ngayNhap;
    private List<ChiTietResponse> chiTietList;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ChiTietResponse {
        private String maNguyenLieu;
        private Integer soLuong;
        private java.math.BigDecimal donGia;
    }
}
