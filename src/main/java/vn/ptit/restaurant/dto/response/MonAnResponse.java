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
public class MonAnResponse {
    private String maMon;
    private String tenMon;
    private BigDecimal donGia;
    private Integer thoiGianCheBien;
    private String maNhomMon;
}
