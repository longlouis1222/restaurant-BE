package vn.ptit.restaurant.dto.request;

import lombok.*;
import java.util.List;

@Data
public class TaoHoaDonRequest {

    private String banId;

    // Thông tin khách hàng đi kèm hóa đơn (không bắt buộc)
    private String tenKhachHang;
    private String soDienThoai;

    private List<ChiTietRequest> chiTietList;

    @Data
    public static class ChiTietRequest {
        private String monAnId;
        private Integer soLuong;
    }
}