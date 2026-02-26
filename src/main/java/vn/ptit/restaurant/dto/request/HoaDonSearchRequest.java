package vn.ptit.restaurant.dto.request;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class HoaDonSearchRequest {

    private Long id;
    private Long banId;
    private String trangThai;

    private LocalDateTime tuNgay;
    private LocalDateTime denNgay;

    private BigDecimal tongTienTu;
    private BigDecimal tongTienDen;

    private int page = 0;
    private int size = 10;
    private String sortBy = "thoiGianTao";
    private String sortDir = "desc";
}