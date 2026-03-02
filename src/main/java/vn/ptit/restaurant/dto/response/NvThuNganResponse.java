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
public class NvThuNganResponse {
    private String maNhanVien;
    private String caThuNgan;
    private BigDecimal tongTienXuLy;
}
