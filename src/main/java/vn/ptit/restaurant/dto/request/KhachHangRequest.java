package vn.ptit.restaurant.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KhachHangRequest {
    private String maKhachHang;

    @NotBlank
    private String tenKhachHang;

    private String sdt;
    private String diaChi;
    private LocalDate ngaySinh;
    private java.math.BigDecimal diemTichLuy;
}
