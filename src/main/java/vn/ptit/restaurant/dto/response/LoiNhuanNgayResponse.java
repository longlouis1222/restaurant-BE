package vn.ptit.restaurant.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class LoiNhuanNgayResponse {

    private LocalDate ngay;
    private BigDecimal doanhThu;
    private BigDecimal chiPhi;
    private BigDecimal loiNhuan;
}
