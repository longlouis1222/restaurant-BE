package vn.ptit.restaurant.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class DoanhThuResponse {
    private BigDecimal tongDoanhThu;
}