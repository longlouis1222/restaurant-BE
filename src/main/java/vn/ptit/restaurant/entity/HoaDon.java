package vn.ptit.restaurant.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "hoa_don")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HoaDon {

    @Id
    @Column(name = "ma_hoa_don")
    private String maHoaDon;

    @ManyToOne
    @JoinColumn(name = "ma_khach_hang")
    private KhachHang khachHang;

    @ManyToOne
    @JoinColumn(name = "ma_ban")
    private BanAn banAn;

    @ManyToOne
    @JoinColumn(name = "ma_nhan_vien")
    private NhanVien nhanVien;

    @Column(name = "ngay_lap")
    private LocalDateTime ngayLap;

    @Column(name = "tong_tien")
    private BigDecimal tongTien;

    @Enumerated(EnumType.STRING)
    private TrangThaiHoaDon trangThai;

    // ================= ENUM =================
    public enum TrangThaiHoaDon {
        NEW,        // Mới tạo
        PAID,       // Đã thanh toán
        CANCELLED   // Đã hủy
    }
}