package vn.ptit.restaurant.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptit.restaurant.dto.response.*;
import vn.ptit.restaurant.service.BaoCaoService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/bao-cao")
@RequiredArgsConstructor
public class BaoCaoController {

    private final BaoCaoService baoCaoService;

    @GetMapping("/tong-doanh-thu")
    public ResponseEntity<DoanhThuResponse> tongDoanhThu(
            @RequestParam LocalDateTime tuNgay,
            @RequestParam LocalDateTime denNgay) {

        return ResponseEntity.ok(
                baoCaoService.tinhTongDoanhThu(tuNgay, denNgay)
        );
    }

    @GetMapping("/theo-thang")
    public ResponseEntity<List<DoanhThuTheoThangResponse>> theoThang(
            @RequestParam int nam) {

        return ResponseEntity.ok(
                baoCaoService.doanhThuTheoThang(nam)
        );
    }

    // Thống kê bàn ăn: trung bình số bàn từng loại được sử dụng theo ngày trong tháng
    @GetMapping("/ban-an/trung-binh-theo-ngay")
    public ResponseEntity<List<BanAnThongKeResponse>> thongKeBanAnTrungBinhTheoNgayTrongThang(
            @RequestParam int nam,
            @RequestParam int thang
    ) {
        List<BanAnThongKeResponse> result = baoCaoService.thongKeBanAnTrungBinhTheoNgayTrongThang(nam, thang);
        return ResponseEntity.ok(result);
    }

    // Thống kê tần suất gọi món trong một tháng
    @GetMapping("/mon-an/thong-ke-theo-thang")
    public ResponseEntity<List<MonAnStatisticsResponse>> thongKeMonAnTheoThang(
            @RequestParam int nam,
            @RequestParam int thang
    ) {
        List<MonAnStatisticsResponse> result = baoCaoService.thongKeMonAnTheoThang(nam, thang);
        return ResponseEntity.ok(result);
    }

    // Top món bán chạy trong khoảng thời gian (ngày)
    @GetMapping("/mon-an/top")
    public ResponseEntity<PageResponse<MonAnStatisticsResponse>> topMonAnByThoiGian(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageResponse<MonAnStatisticsResponse> result = baoCaoService.topMonAnByThoiGian(fromDate, toDate, page, size);
        return ResponseEntity.ok(result);
    }

    // Thống kê nguyên liệu: lượng sử dụng và chi phí theo ngày trong khoảng thời gian
    @GetMapping("/nguyen-lieu/theo-ngay")
    public ResponseEntity<List<NguyenLieuThongKeNgayResponse>> thongKeNguyenLieuTheoNgay(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate
    ) {
        List<NguyenLieuThongKeNgayResponse> result = baoCaoService.thongKeNguyenLieuTheoNgay(fromDate, toDate);
        return ResponseEntity.ok(result);
    }

    // Thống kê nhà cung cấp: sắp xếp theo tổng lượng nguyên liệu cung cấp trong tháng
    @GetMapping("/nha-cung-cap/theo-thang")
    public ResponseEntity<List<NhaCungCapThongKeResponse>> thongKeNhaCungCapTheoThang(
            @RequestParam int nam,
            @RequestParam int thang
    ) {
        List<NhaCungCapThongKeResponse> result = baoCaoService.thongKeNhaCungCapTheoThang(nam, thang);
        return ResponseEntity.ok(result);
    }

    // Thống kê lương nhân viên theo tháng
    @GetMapping("/nhan-vien/luong-theo-thang")
    public ResponseEntity<List<LuongNhanVienResponse>> thongKeLuongNhanVienTheoThang(
            @RequestParam int nam,
            @RequestParam int thang
    ) {
        List<LuongNhanVienResponse> result = baoCaoService.thongKeLuongNhanVienTheoThang(nam, thang);
        return ResponseEntity.ok(result);
    }
}