package vn.ptit.restaurant.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.ptit.restaurant.entity.BanAn;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BanAnResponse {
    private String maBan;
    private Integer soCho;
    private String khuVuc;
    private Integer tanSuatSuDung;
    private BanAn.TrangThaiBan trangThai;

    // Thông tin hóa đơn hiện tại của bàn (nếu có)
    private String maHoaDon;
    private String tenKhach;
    private java.math.BigDecimal tongTienHoaDon;
}
