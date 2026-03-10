package vn.ptit.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.ptit.restaurant.dto.response.NhaCungCapThongKeResponse;
import vn.ptit.restaurant.dto.response.NguyenLieuThongKeNgayResponse;
import vn.ptit.restaurant.dto.response.ChiPhiNgayResponse;
import vn.ptit.restaurant.entity.ChiTietPhieuNhap;
import vn.ptit.restaurant.entity.id.ChiTietPhieuNhapId;

import java.time.LocalDateTime;
import java.util.List;

public interface ChiTietPhieuNhapRepository
        extends JpaRepository<ChiTietPhieuNhap, ChiTietPhieuNhapId> {

    List<ChiTietPhieuNhap> findByPhieuNhapMaPhieuNhap(String maPhieuNhap);

    // Thống kê lượng nguyên liệu và chi phí theo ngày trong khoảng thời gian
    @Query("SELECT new vn.ptit.restaurant.dto.response.NguyenLieuThongKeNgayResponse(" +
            "FUNCTION('DATE', p.ngayNhap), n.maNguyenLieu, n.tenNguyenLieu, " +
            "COALESCE(SUM(c.soLuong), 0), COALESCE(SUM(c.soLuong * c.donGia), 0)) " +
            "FROM ChiTietPhieuNhap c " +
            "JOIN c.phieuNhap p " +
            "JOIN c.nguyenLieu n " +
            "WHERE p.ngayNhap BETWEEN :fromDate AND :toDate " +
            "GROUP BY FUNCTION('DATE', p.ngayNhap), n.maNguyenLieu, n.tenNguyenLieu " +
            "ORDER BY FUNCTION('DATE', p.ngayNhap), n.maNguyenLieu")
    List<NguyenLieuThongKeNgayResponse> thongKeNguyenLieuTheoNgay(@Param("fromDate") LocalDateTime fromDate,
                                                                   @Param("toDate") LocalDateTime toDate);

    // Tổng chi phí nguyên liệu theo ngày trong khoảng thời gian
    @Query("SELECT new vn.ptit.restaurant.dto.response.ChiPhiNgayResponse(" +
            "FUNCTION('DATE', p.ngayNhap), " +
            "COALESCE(SUM(c.soLuong * c.donGia), 0)) " +
            "FROM ChiTietPhieuNhap c " +
            "JOIN c.phieuNhap p " +
            "WHERE p.ngayNhap BETWEEN :fromDate AND :toDate " +
            "GROUP BY FUNCTION('DATE', p.ngayNhap) " +
            "ORDER BY FUNCTION('DATE', p.ngayNhap)")
    List<ChiPhiNgayResponse> chiPhiTheoNgay(@Param("fromDate") LocalDateTime fromDate,
                                             @Param("toDate") LocalDateTime toDate);

    // Thống kê nhà cung cấp: tổng lượng nguyên liệu và chi phí trong một tháng, sắp xếp theo tổng lượng giảm dần
    @Query("SELECT new vn.ptit.restaurant.dto.response.NhaCungCapThongKeResponse(" +
            "ncc.maNcc, ncc.tenNcc, COALESCE(SUM(c.soLuong), 0), COALESCE(SUM(c.soLuong * c.donGia), 0)) " +
            "FROM ChiTietPhieuNhap c " +
            "JOIN c.phieuNhap p " +
            "JOIN p.nhaCungCap ncc " +
            "WHERE FUNCTION('YEAR', p.ngayNhap) = :nam " +
            "AND FUNCTION('MONTH', p.ngayNhap) = :thang " +
            "GROUP BY ncc.maNcc, ncc.tenNcc " +
            "ORDER BY SUM(c.soLuong) DESC")
    List<NhaCungCapThongKeResponse> thongKeNhaCungCapTheoThang(@Param("nam") int nam,
                                                                @Param("thang") int thang);
}