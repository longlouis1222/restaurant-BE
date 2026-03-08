package vn.ptit.restaurant.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class NvPhucVuOrderResponse {

    private Long orderId;
    private String maBan;
    private String maNhanVienPhucVu;
    private List<OrderItemResponse> dsMon;
    private String trangThai;

    private LocalDateTime thoiGianDatMon;
    private LocalDateTime thoiGianHoanThanhMon;
    private LocalDateTime thoiGianPhucVu;
    private LocalDateTime thoiGianThanhToan;

    @Data
    @Builder
    public static class OrderItemResponse {
        private String maMon;
        private String tenMon;
        private Integer soLuong;
        private Double donGia;
        private Double thanhTien;
        private String trangThai;
        private LocalDateTime thoiGianHoanThanhMon;
    }
}
