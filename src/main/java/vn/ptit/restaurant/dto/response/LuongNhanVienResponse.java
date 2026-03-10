package vn.ptit.restaurant.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class LuongNhanVienResponse {

    private String maNhanVien;
    private String tenNhanVien;
    private String maChucVu;
    private String tenChucVu;
    private BigDecimal luongCoBan;
    private Long tongKhachTrongThang;
    private BigDecimal thuong;
    private BigDecimal luongThucNhan;
}
