package vn.ptit.restaurant.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhieuNhapRequest {
    private String maPhieuNhap;

    @NotBlank
    private String maNcc;

    @NotBlank
    private String maNhanVien;

    @NotNull
    private LocalDateTime ngayNhap;

    @Valid
    @NotEmpty
    private List<ChiTietRequest> chiTietList;

    @Data
    public static class ChiTietRequest {
        @NotBlank
        private String maNguyenLieu;

        @NotNull
        @Min(1)
        private Integer soLuong;

        @NotNull
        @DecimalMin(value = "0.0", inclusive = false)
        private java.math.BigDecimal donGia;
    }
}
