package vn.ptit.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.ptit.restaurant.dto.response.DoanhThuTheoThangResponse;
import vn.ptit.restaurant.entity.HoaDon;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface HoaDonRepository
        extends JpaRepository<HoaDon, String>,
        JpaSpecificationExecutor<HoaDon> {

    @Query("SELECT COALESCE(SUM(h.tongTien), 0) " +
            "FROM HoaDon h " +
            "WHERE h.ngayLap BETWEEN :tuNgay AND :denNgay")
    BigDecimal tinhTongDoanhThu(
            @Param("tuNgay") LocalDateTime tuNgay,
            @Param("denNgay") LocalDateTime denNgay
    );

    @Query("SELECT h.banAn.soCho, COUNT(h) " +
            "FROM HoaDon h " +
            "WHERE h.ngayLap BETWEEN :tuNgay AND :denNgay " +
            "GROUP BY h.banAn.soCho")
    List<Object[]> demSoLanSuDungBanTheoSoCho(
            @Param("tuNgay") LocalDateTime tuNgay,
            @Param("denNgay") LocalDateTime denNgay
    );

    @Query("SELECT new vn.ptit.restaurant.dto.response.DoanhThuTheoThangResponse(" +
            "FUNCTION('MONTH', h.ngayLap), " +
            "COALESCE(SUM(h.tongTien), 0)) " +
            "FROM HoaDon h " +
            "WHERE FUNCTION('YEAR', h.ngayLap) = :nam " +
            "GROUP BY FUNCTION('MONTH', h.ngayLap) " +
            "ORDER BY FUNCTION('MONTH', h.ngayLap)")
    List<DoanhThuTheoThangResponse> doanhThuTheoThang(
            @Param("nam") int nam
    );

    // Đếm tổng số khách (hóa đơn PAID) trong một tháng
    @Query("SELECT COUNT(h) " +
            "FROM HoaDon h " +
            "WHERE FUNCTION('YEAR', h.ngayLap) = :nam " +
            "AND FUNCTION('MONTH', h.ngayLap) = :thang " +
            "AND h.trangThai = vn.ptit.restaurant.entity.HoaDon$TrangThaiHoaDon.PAID")
    Long demTongKhachTrongThang(@Param("nam") int nam,
                                 @Param("thang") int thang);

    // Đếm tổng số khách (hóa đơn PAID) theo từng nhân viên trong một tháng
    @Query("SELECT h.nhanVien.maNhanVien, h.nhanVien.tenNhanVien, COUNT(h) " +
            "FROM HoaDon h " +
            "WHERE FUNCTION('YEAR', h.ngayLap) = :nam " +
            "AND FUNCTION('MONTH', h.ngayLap) = :thang " +
            "AND h.trangThai = vn.ptit.restaurant.entity.HoaDon$TrangThaiHoaDon.PAID " +
            "GROUP BY h.nhanVien.maNhanVien, h.nhanVien.tenNhanVien")
    List<Object[]> demKhachTheoNhanVienTrongThang(@Param("nam") int nam,
                                                  @Param("thang") int thang);
}