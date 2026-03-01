package vn.ptit.restaurant.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NhanVienRequest {
    private String maNhanVien;

    @NotBlank
    private String tenNhanVien;

    private String soDienThoai;
    private LocalDate ngaySinh;
    private LocalDateTime ngayVaoLam;
    private String cccd;
    private String maChucVu;
}
