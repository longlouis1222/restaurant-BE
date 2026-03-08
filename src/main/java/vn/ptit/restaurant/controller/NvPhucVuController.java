package vn.ptit.restaurant.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.ptit.restaurant.common.ApiResponse;
import vn.ptit.restaurant.common.catalog.AbstractCatalogController;
import vn.ptit.restaurant.dto.request.NvPhucVuRequest;
import vn.ptit.restaurant.dto.request.NvPhucVuSearchRequest;
import vn.ptit.restaurant.dto.request.ThemMonRequest;
import vn.ptit.restaurant.dto.response.NvPhucVuResponse;
import vn.ptit.restaurant.dto.response.OrderResponse;
import vn.ptit.restaurant.dto.response.PageResponse;
import vn.ptit.restaurant.service.NvPhucVuService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/nv-phuc-vu")
public class NvPhucVuController extends AbstractCatalogController<String, NvPhucVuRequest, NvPhucVuRequest, NvPhucVuResponse, NvPhucVuSearchRequest> {

    private final NvPhucVuService nvPhucVuService;

    public NvPhucVuController(NvPhucVuService service) {
        super(service);
        this.nvPhucVuService = service;
    }

    // --- API order có thể gọi trực tiếp từ Postman ---

    @PostMapping("/orders")
    public ResponseEntity<ApiResponse<OrderResponse>> taoOrder(@RequestParam("maBan") String maBan,
                                                                @RequestParam("maNhanVienPhucVu") String maNhanVienPhucVu,
                                                                @Validated @RequestBody List<ThemMonRequest> dsMon) {
        OrderResponse res = nvPhucVuService.taoOrder(maBan, maNhanVienPhucVu, dsMon);
        return ResponseEntity.ok(ApiResponse.success(res, "Tạo order thành công"));
    }

    @PutMapping("/orders/{orderId}")
    public ResponseEntity<ApiResponse<OrderResponse>> capNhatOrder(@PathVariable("orderId") String orderId,
                                                                    @Validated @RequestBody List<ThemMonRequest> dsMon) {
        OrderResponse res = nvPhucVuService.capNhatOrder(orderId, dsMon);
        return ResponseEntity.ok(ApiResponse.success(res, "Cập nhật order thành công"));
    }

    @PostMapping("/orders/{orderId}/gui-bep")
    public ResponseEntity<ApiResponse<OrderResponse>> guiOrderSangBep(@PathVariable("orderId") String orderId,
                                                                       @RequestParam("maNhanVienPhucVu") String maNhanVienPhucVu) {
        OrderResponse res = nvPhucVuService.guiOrderSangBep(orderId, maNhanVienPhucVu);
        return ResponseEntity.ok(ApiResponse.success(res, "Gửi order sang bếp thành công"));
    }

    @GetMapping("/{maNhanVienPhucVu}/orders/san-sang-phuc-vu")
    public ResponseEntity<ApiResponse<PageResponse<OrderResponse>>> layDsOrderSanSangPhucVu(@PathVariable("maNhanVienPhucVu") String maNhanVienPhucVu,
                                                                                            @RequestParam(value = "page", defaultValue = "0") int page,
                                                                                            @RequestParam(value = "size", defaultValue = "20") int size) {
        PageResponse<OrderResponse> res = nvPhucVuService.layDsOrderSanSangPhucVu(maNhanVienPhucVu, page, size);
        return ResponseEntity.ok(ApiResponse.success(res));
    }

    @PostMapping("/orders/{orderId}/xac-nhan-phuc-vu")
    public ResponseEntity<ApiResponse<OrderResponse>> xacNhanPhucVu(@PathVariable("orderId") String orderId,
                                                                     @RequestParam("maNhanVienPhucVu") String maNhanVienPhucVu,
                                                                     @RequestParam(value = "thoiGianPhucVu", required = false) LocalDateTime thoiGianPhucVu) {
        OrderResponse res = nvPhucVuService.xacNhanPhucVu(orderId, maNhanVienPhucVu, thoiGianPhucVu);
        return ResponseEntity.ok(ApiResponse.success(res, "Xác nhận phục vụ thành công"));
    }

    @PostMapping("/orders/{orderId}/yeu-cau-thanh-toan")
    public ResponseEntity<ApiResponse<OrderResponse>> yeuCauThanhToan(@PathVariable("orderId") String orderId,
                                                                       @RequestParam("maNhanVienPhucVu") String maNhanVienPhucVu) {
        OrderResponse res = nvPhucVuService.yeuCauThanhToan(orderId, maNhanVienPhucVu);
        return ResponseEntity.ok(ApiResponse.success(res, "Yêu cầu thanh toán thành công"));
    }

    @PostMapping("/orders/tra-cuu-theo-nhan-vien")
    public ResponseEntity<ApiResponse<PageResponse<OrderResponse>>> traCuuOrderTheoNhanVien(@RequestParam("maNhanVienPhucVu") String maNhanVienPhucVu,
                                                                                            @RequestParam(value = "thoiGianBatDau", required = false) LocalDateTime thoiGianBatDau,
                                                                                            @RequestParam(value = "thoiGianKetThuc", required = false) LocalDateTime thoiGianKetThuc,
                                                                                            @RequestParam(value = "trangThai", required = false) String trangThai,
                                                                                            @RequestParam(value = "page", defaultValue = "0") int page,
                                                                                            @RequestParam(value = "size", defaultValue = "20") int size) {
        PageResponse<OrderResponse> res = nvPhucVuService.traCuuOrderTheoNhanVien(maNhanVienPhucVu, thoiGianBatDau, thoiGianKetThuc, trangThai, page, size);
        return ResponseEntity.ok(ApiResponse.success(res));
    }
}
