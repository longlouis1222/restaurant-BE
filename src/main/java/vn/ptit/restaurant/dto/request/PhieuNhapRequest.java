package vn.ptit.restaurant.dto.request;

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
public class PhieuNhapRequest {
    private String maPhieuNhap;
    private String maNcc;
    private String maNhanVien;
    private LocalDateTime ngayNhap;

    private List<ChiTietRequest> chiTietList;

    @Data
    public static class ChiTietRequest {
        private String maNguyenLieu;
        private Integer soLuong;
        private java.math.BigDecimal donGia;
    }
}
