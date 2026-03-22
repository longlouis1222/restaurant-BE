package vn.ptit.restaurant.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NhanVienResponse {
    private String maNhanVien;
    private String tenNhanVien;
    private String soDienThoai;
    private LocalDate ngaySinh;
    private LocalDateTime ngayVaoLam;
    private String cccd;
    private String maChucVu;

    // Thông tin chi tiết theo vai trò (nếu có)
    private NvKhoBepResponse nvKhoBep;
    private NvPhucVuResponse nvPhucVu;
    private NvThuNganResponse nvThuNgan;
}
