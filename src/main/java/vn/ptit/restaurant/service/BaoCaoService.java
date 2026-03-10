package vn.ptit.restaurant.service;

import vn.ptit.restaurant.dto.*;
import vn.ptit.restaurant.dto.response.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface BaoCaoService {

    DoanhThuResponse tinhTongDoanhThu(LocalDateTime tuNgay, LocalDateTime denNgay);

//    List<DoanhThuTheoNgayResponse> doanhThuTheoNgay(LocalDateTime tuNgay, LocalDateTime denNgay);
//
    List<DoanhThuTheoThangResponse> doanhThuTheoThang(int nam);

    // Thống kê trung bình số bàn từng loại được sử dụng theo ngày trong một tháng
    List<BanAnThongKeResponse> thongKeBanAnTrungBinhTheoNgayTrongThang(int nam, int thang);

    // Thống kê tần suất gọi món trong một tháng
    List<MonAnStatisticsResponse> thongKeMonAnTheoThang(int nam, int thang);

    // Top món bán chạy trong một khoảng thời gian (theo ngày, dạng LocalDate)
    PageResponse<MonAnStatisticsResponse> topMonAnByThoiGian(LocalDate fromDate,
                                                             LocalDate toDate,
                                                             int page,
                                                             int size);

    // Thống kê nguyên liệu: lượng sử dụng và tổng chi phí theo ngày trong khoảng thời gian
    List<NguyenLieuThongKeNgayResponse> thongKeNguyenLieuTheoNgay(LocalDate fromDate,
                                                                   LocalDate toDate);

    // Thống kê nhà cung cấp: sắp xếp theo tổng lượng nguyên liệu cung cấp trong tháng
    List<NhaCungCapThongKeResponse> thongKeNhaCungCapTheoThang(int nam, int thang);

    // Thống kê lương nhân viên theo tháng
    List<LuongNhanVienResponse> thongKeLuongNhanVienTheoThang(int nam, int thang);
}