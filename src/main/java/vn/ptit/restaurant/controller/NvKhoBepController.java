package vn.ptit.restaurant.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptit.restaurant.common.ApiResponse;
import vn.ptit.restaurant.common.catalog.AbstractCatalogController;
import vn.ptit.restaurant.dto.request.NvKhoBepRequest;
import vn.ptit.restaurant.dto.request.NvKhoBepSearchRequest;
import vn.ptit.restaurant.dto.response.NvKhoBepResponse;
import vn.ptit.restaurant.dto.response.OrderResponse;
import vn.ptit.restaurant.service.NvKhoBepService;

@RestController
@RequestMapping("/api/nv-kho-bep")
public class NvKhoBepController extends AbstractCatalogController<String, NvKhoBepRequest, NvKhoBepRequest, NvKhoBepResponse, NvKhoBepSearchRequest> {

    private final NvKhoBepService nvKhoBepService;

    public NvKhoBepController(NvKhoBepService service) {
        super(service);
        this.nvKhoBepService = service;
    }

    // --- Chữ ký method cho luồng bếp: nhận và hoàn thành món ---

    // Lấy danh sách order/món đang chờ bếp chế biến
    @GetMapping("/orders/cho-che-bien")
    public ResponseEntity<ApiResponse<?>> layDsOrderChoCheBien() {
        // ...implementation sẽ được bổ sung sau...
        return null;
    }

    // Bếp xác nhận hoàn thành toàn bộ món của một order
    @PostMapping("/orders/{orderId}/hoan-thanh")
    public ResponseEntity<ApiResponse<OrderResponse>> hoanThanhOrder(@PathVariable("orderId") String orderId,
                                                                      @RequestParam("maNhanVienBep") String maNhanVienBep) {
        // ...implementation sẽ được bổ sung sau...
        return null;
    }
}
