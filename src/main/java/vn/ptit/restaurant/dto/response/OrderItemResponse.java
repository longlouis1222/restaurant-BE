package vn.ptit.restaurant.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class OrderItemResponse {

    private String maMon;
    private String tenMon;
    private Integer soLuong;
    private BigDecimal donGia;
    private BigDecimal thanhTien;

    // Thời gian hoàn thành món (từ bếp)
    private LocalDateTime thoiGianHoanThanhMon;
}
