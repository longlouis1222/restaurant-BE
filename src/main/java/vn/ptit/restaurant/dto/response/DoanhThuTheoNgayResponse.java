package vn.ptit.restaurant.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class DoanhThuTheoNgayResponse {
    private LocalDate ngay;
    private BigDecimal doanhThu;

//    public DoanhThuTheoNgayResponse(java.sql.Date ngay, BigDecimal doanhThu) {
//        this.ngay = ngay.toLocalDate();
//        this.doanhThu = doanhThu;
//    }
}