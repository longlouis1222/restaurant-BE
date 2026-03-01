package vn.ptit.restaurant.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KhachHangResponse {
    private String maKhachHang;
    private String tenKhachHang;
    private String sdt;
    private String diaChi;
    private LocalDate ngaySinh;
    private BigDecimal diemTichLuy;
}
