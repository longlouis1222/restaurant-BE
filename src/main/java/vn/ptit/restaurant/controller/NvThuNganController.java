package vn.ptit.restaurant.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptit.restaurant.common.ApiResponse;
import vn.ptit.restaurant.common.catalog.AbstractCatalogController;
import vn.ptit.restaurant.dto.request.NvThuNganRequest;
import vn.ptit.restaurant.dto.request.NvThuNganSearchRequest;
import vn.ptit.restaurant.dto.response.NvThuNganResponse;
import vn.ptit.restaurant.dto.response.OrderResponse;
import vn.ptit.restaurant.dto.response.PageResponse;
import vn.ptit.restaurant.service.NvThuNganService;

@RestController
@RequestMapping("/api/nv-thu-ngan")
public class NvThuNganController extends AbstractCatalogController<String, NvThuNganRequest, NvThuNganRequest, NvThuNganResponse, NvThuNganSearchRequest> {

    private final NvThuNganService nvThuNganService;

    public NvThuNganController(NvThuNganService service) {
        super(service);
        this.nvThuNganService = service;
    }

    // --- Chữ ký method cho luồng thu ngân: thanh toán hóa đơn ---

    // Lấy danh sách hóa đơn/bàn đang chờ thanh toán
    @GetMapping("/bills/cho-thanh-toan")
    public ResponseEntity<ApiResponse<PageResponse<OrderResponse>>> layDsHoaDonChoThanhToan(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size) {
        // ...implementation sẽ được bổ sung sau...
        return null;
    }

    // Thu ngân thực hiện thanh toán cho một hóa đơn, cập nhật thời gian thanh toán
    @PostMapping("/bills/{maHoaDon}/thanh-toan")
    public ResponseEntity<ApiResponse<OrderResponse>> thanhToanHoaDon(@PathVariable("maHoaDon") String maHoaDon,
                                                                       @RequestParam("maNhanVienThuNgan") String maNhanVienThuNgan) {
        // ...implementation sẽ được bổ sung sau...
        return null;
    }
}
