package vn.ptit.restaurant.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonAnStatisticsResponse {
    private String maMon;
    private String tenMon;
    private Long tongSoLuong;
    private BigDecimal tongDoanhThu;

    public MonAnStatisticsResponse(String maMon, String tenMon, Long tongSoLuong) {
        this.maMon = maMon;
        this.tenMon = tenMon;
        this.tongSoLuong = tongSoLuong;
        this.tongDoanhThu = BigDecimal.ZERO;
    }
}
