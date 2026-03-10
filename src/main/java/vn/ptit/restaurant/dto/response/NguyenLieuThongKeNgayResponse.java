package vn.ptit.restaurant.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
public class NguyenLieuThongKeNgayResponse {

    private Date ngay;
    private String maNguyenLieu;
    private String tenNguyenLieu;
    private Long tongSoLuong;
    private BigDecimal tongChiPhi;
}
