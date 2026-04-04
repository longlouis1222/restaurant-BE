package vn.ptit.restaurant.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
public class DoanhThuTheoNgayResponse {
    private Date ngay;
    private BigDecimal doanhThu;
}