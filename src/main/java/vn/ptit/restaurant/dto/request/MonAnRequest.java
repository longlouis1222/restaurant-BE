package vn.ptit.restaurant.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MonAnRequest {
    private String maMon;

    @NotBlank
    private String tenMon;

    @NotNull
    private BigDecimal donGia;

    private Integer thoiGianCheBien;
    private String maNhomMon;
}
