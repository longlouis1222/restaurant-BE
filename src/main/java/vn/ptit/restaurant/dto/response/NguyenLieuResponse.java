package vn.ptit.restaurant.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NguyenLieuResponse {
    private String maNguyenLieu;
    private String tenNguyenLieu;
    private String donViTinh;
    private BigDecimal giaNhap;
}
