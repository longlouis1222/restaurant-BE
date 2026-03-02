package vn.ptit.restaurant.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NvThuNganRequest {
    @NotBlank(message = "maNhanVien is required")
    private String maNhanVien;

    @NotBlank(message = "caThuNgan is required")
    private String caThuNgan;

    @NotNull(message = "tongTienXuLy is required")
    @DecimalMin(value = "0", message = "tongTienXuLy must be >= 0")
    private BigDecimal tongTienXuLy;
}
