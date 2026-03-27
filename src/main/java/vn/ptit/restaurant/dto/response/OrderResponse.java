package vn.ptit.restaurant.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderResponse {

    // Map với HoaDon
    private String maHoaDon;
    private String maBan;
    private String maKhachHang;
    private String maNhanVienPhucVu;

    // Thông tin khách
    private String tenKhachHang;
    private String soDienThoaiKhach;

    private BigDecimal tongTien;
    private String trangThaiHoaDon;

    // Các mốc thời gian chính
    private LocalDateTime thoiGianDatMon;        // thời điểm gửi order xuống bếp
    private LocalDateTime thoiGianHoanThanhMon;  // thời điểm tất cả món trong order hoàn thành
    private LocalDateTime thoiGianPhucVu;        // thời điểm NV phục vụ mang món ra bàn xong
    private LocalDateTime thoiGianThanhToan;     // thời điểm thu ngân thanh toán xong

    private List<OrderItemResponse> dsMon;
}
