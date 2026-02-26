package vn.ptit.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.ptit.restaurant.dto.response.DoanhThuTheoNgayResponse;
import vn.ptit.restaurant.dto.response.DoanhThuTheoThangResponse;
import vn.ptit.restaurant.entity.HoaDon;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface HoaDonRepository
        extends JpaRepository<HoaDon, Long>,
        JpaSpecificationExecutor<HoaDon> {

    @Query("SELECT COALESCE(SUM(h.tongTien), 0) " +
            "FROM HoaDon h " +
            "WHERE h.ngayTao BETWEEN :tuNgay AND :denNgay")
    BigDecimal tinhTongDoanhThu(
            @Param("tuNgay") LocalDateTime tuNgay,
            @Param("denNgay") LocalDateTime denNgay
    );

    @Query("SELECT new vn.ptit.restaurant.dto.response.DoanhThuTheoNgayResponse(" +
            "FUNCTION('DATE', h.ngayTao), " +
            "COALESCE(SUM(h.tongTien), 0)) " +
            "FROM HoaDon h " +
            "WHERE h.ngayTao BETWEEN :tuNgay AND :denNgay " +
            "GROUP BY FUNCTION('DATE', h.ngayTao) " +
            "ORDER BY FUNCTION('DATE', h.ngayTao)")
    List<DoanhThuTheoNgayResponse> doanhThuTheoNgay(
            @Param("tuNgay") LocalDateTime tuNgay,
            @Param("denNgay") LocalDateTime denNgay
    );

    @Query("SELECT new vn.ptit.restaurant.dto.response.DoanhThuTheoThangResponse(" +
            "FUNCTION('MONTH', h.ngayTao), " +
            "COALESCE(SUM(h.tongTien), 0)) " +
            "FROM HoaDon h " +
            "WHERE FUNCTION('YEAR', h.ngayTao) = :nam " +
            "GROUP BY FUNCTION('MONTH', h.ngayTao) " +
            "ORDER BY FUNCTION('MONTH', h.ngayTao)")
    List<DoanhThuTheoThangResponse> doanhThuTheoThang(
            @Param("nam") int nam
    );
}