package vn.ptit.restaurant.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
public class ChiPhiNgayResponse {

    private Date ngay;
    private BigDecimal tongChiPhi;
}
