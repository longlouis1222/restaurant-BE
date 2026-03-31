package vn.ptit.restaurant.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.ptit.restaurant.dto.response.MonAnStatisticsResponse;
import vn.ptit.restaurant.entity.ChiTietHoaDon;
import vn.ptit.restaurant.entity.id.ChiTietHoaDonId;

import java.time.LocalDateTime;
import java.util.List;

public interface ChiTietHoaDonRepository
        extends JpaRepository<ChiTietHoaDon, ChiTietHoaDonId> {

    // Thống kê tần suất gọi món theo tháng (năm + tháng)
    @Query("SELECT new vn.ptit.restaurant.dto.response.MonAnStatisticsResponse(" +
            "m.maMon, m.tenMon, COALESCE(SUM(c.soLuong), 0), COALESCE(SUM(c.soLuong * c.donGia), 0)) " +
            "FROM ChiTietHoaDon c " +
            "JOIN c.hoaDon h " +
            "JOIN c.monAn m " +
            "WHERE FUNCTION('YEAR', h.ngayLap) = :nam " +
            "AND FUNCTION('MONTH', h.ngayLap) = :thang " +
            "AND h.trangThai = vn.ptit.restaurant.entity.HoaDon$TrangThaiHoaDon.PAID " +
            "GROUP BY m.maMon, m.tenMon")
    java.util.List<MonAnStatisticsResponse> thongKeMonAnTheoThang(@Param("nam") int nam,
                                                                  @Param("thang") int thang);

    // Top món bán chạy trong khoảng thời gian, sắp xếp giảm dần theo tổng số lượng
    @Query("SELECT new vn.ptit.restaurant.dto.response.MonAnStatisticsResponse(" +
            "m.maMon, m.tenMon, COALESCE(SUM(c.soLuong), 0), COALESCE(SUM(c.soLuong * c.donGia), 0)) " +
            "FROM ChiTietHoaDon c " +
            "JOIN c.hoaDon h " +
            "JOIN c.monAn m " +
            "WHERE h.ngayLap BETWEEN :fromDate AND :toDate " +
            "AND h.trangThai = vn.ptit.restaurant.entity.HoaDon$TrangThaiHoaDon.PAID " +
            "GROUP BY m.maMon, m.tenMon " +
            "ORDER BY SUM(c.soLuong) DESC")
    Page<MonAnStatisticsResponse> topMonAnByThoiGian(@Param("fromDate") LocalDateTime fromDate,
                                                     @Param("toDate") LocalDateTime toDate,
                                                     Pageable pageable);

    List<ChiTietHoaDon> findByHoaDon_MaHoaDon(String maHoaDon);

    // Lấy toàn bộ chi tiết hóa đơn theo danh sách mã hoá đơn (dùng để tránh N+1 query)
    List<ChiTietHoaDon> findByHoaDon_MaHoaDonIn(List<String> maHoaDonList);

    // Xóa toàn bộ chi tiết theo mã hóa đơn (dùng cho cập nhật hóa đơn)
    void deleteAllByHoaDon_MaHoaDon(String maHoaDon);
}