package vn.ptit.restaurant.controller;

import vn.ptit.restaurant.common.ApiResponse;
import vn.ptit.restaurant.dto.request.HoaDonSearchRequest;
import vn.ptit.restaurant.dto.request.TaoHoaDonRequest;
import vn.ptit.restaurant.dto.response.HoaDonResponse;
import vn.ptit.restaurant.dto.response.PageResponse;
import vn.ptit.restaurant.service.HoaDonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hoa-don")
@RequiredArgsConstructor
public class HoaDonController {

    private final HoaDonService hoaDonService;

    @PostMapping
    public ResponseEntity<ApiResponse<HoaDonResponse>> create(
            @RequestBody TaoHoaDonRequest request) {

        return ResponseEntity.ok(
                ApiResponse.<HoaDonResponse>builder()
                        .success(true)
                        .message("Tạo hóa đơn thành công")
                        .data(hoaDonService.taoHoaDon(request))
                        .build()
        );
    }

    // Cập nhật thông tin hóa đơn (bàn, khách, chi tiết món)
    @PutMapping("/{maHoaDon}")
    public ResponseEntity<ApiResponse<HoaDonResponse>> update(
            @PathVariable String maHoaDon,
            @RequestBody TaoHoaDonRequest request) {

        HoaDonResponse updated = hoaDonService.capNhatHoaDon(maHoaDon, request);

        return ResponseEntity.ok(
                ApiResponse.<HoaDonResponse>builder()
                        .success(true)
                        .message("Cập nhật hóa đơn thành công")
                        .data(updated)
                        .build()
        );
    }

    @PutMapping("/{maHoaDon}/thanh-toan")
    public ResponseEntity<ApiResponse<?>> thanhToan(@PathVariable String maHoaDon) {

        hoaDonService.thanhToan(maHoaDon);

        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Thanh toán thành công")
                        .build()
        );
    }

    @PostMapping("/search")
    public ResponseEntity<PageResponse<HoaDonResponse>> search(
            @RequestBody HoaDonSearchRequest request) {

        return ResponseEntity.ok(hoaDonService.search(request));
    }
}