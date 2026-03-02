package vn.ptit.restaurant.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.*;
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

    @Valid
    private List<ChiTietRequest> chiTietList;

    @Data
    public static class ChiTietRequest {
        @NotBlank(message = "maNguyenLieu is required")
        private String maNguyenLieu;

        @NotNull(message = "soLuong is required")
        @Min(value = 1, message = "soLuong must be at least 1")
        private Integer soLuong;

        @NotNull(message = "donGia is required")
        @DecimalMin(value = "0", inclusive = true, message = "donGia must be >= 0")
        private java.math.BigDecimal donGia;
    }
}
