package vn.ptit.restaurant.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class DoanhThuTheoThangResponse {
    private int thang;
    private BigDecimal doanhThu;
}