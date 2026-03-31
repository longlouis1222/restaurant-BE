package vn.ptit.restaurant.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptit.restaurant.common.catalog.AbstractCatalogController;
import vn.ptit.restaurant.dto.request.BanAnRequest;
import vn.ptit.restaurant.dto.request.BanAnSearchRequest;
import vn.ptit.restaurant.dto.response.BanAnResponse;
import vn.ptit.restaurant.service.BanAnService;

import java.util.List;

@RestController
@RequestMapping("/api/ban-an")
public class BanAnController extends AbstractCatalogController<String, BanAnRequest, BanAnRequest, BanAnResponse, BanAnSearchRequest> {

    private final BanAnService banAnService;

    public BanAnController(BanAnService service) {
        super(service);
        this.banAnService = service;
    }

    // API chuẩn để FE lấy toàn bộ bàn hiện có kèm thông tin hóa đơn hiện tại (nếu có)
    @GetMapping("/with-bill")
    public ResponseEntity<List<BanAnResponse>> getAllWithCurrentBill() {
        return ResponseEntity.ok(banAnService.getAllWithCurrentBill());
    }
}
