package vn.ptit.restaurant.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.ptit.restaurant.common.catalog.BaseSearchRequest;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class LuongSearchRequest extends BaseSearchRequest {

    private String maLuong;

    // Khoảng lương cơ bản để filter
    private BigDecimal mucLuongFrom;
    private BigDecimal mucLuongTo;
}
