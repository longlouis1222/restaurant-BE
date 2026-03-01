package vn.ptit.restaurant.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NguyenLieuRequest {
    private String maNguyenLieu;

    @NotBlank
    private String tenNguyenLieu;

    private String donViTinh;
    private BigDecimal giaNhap;
}
