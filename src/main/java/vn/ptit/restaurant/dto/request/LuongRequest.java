package vn.ptit.restaurant.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LuongRequest {

    private String maLuong;

    @NotNull
    private BigDecimal mucLuong;

    private Integer ngayCongTheoThang;

    private Integer ngayCongThucTe;

    private BigDecimal thuong;

    private Integer phat;

    private String ghiChu;
}
