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

    // Thông tin chi tiết theo vai trò (tùy chọn)
    // Nếu maChucVu tương ứng nhân viên kho bếp thì sử dụng nvKhoBepInfo
    private NvKhoBepRequest nvKhoBepInfo;

    // Nếu maChucVu tương ứng nhân viên phục vụ thì sử dụng nvPhucVuInfo
    private NvPhucVuRequest nvPhucVuInfo;

    // Nếu maChucVu tương ứng nhân viên thu ngân thì sử dụng nvThuNganInfo
    private NvThuNganRequest nvThuNganInfo;
}
