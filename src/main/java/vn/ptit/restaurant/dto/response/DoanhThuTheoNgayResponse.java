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

//    public DoanhThuTheoNgayResponse(java.sql.Date ngay, BigDecimal doanhThu) {
//        this.ngay = ngay.toLocalDate();
//        this.doanhThu = doanhThu;
//    }
}