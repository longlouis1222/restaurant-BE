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
public class LuongResponse {

    private String maLuong;

    private BigDecimal mucLuong;

    private Integer ngayCongTheoThang;

    private Integer ngayCongThucTe;

    private BigDecimal thuong;

    private Integer phat;

    private String ghiChu;
}
