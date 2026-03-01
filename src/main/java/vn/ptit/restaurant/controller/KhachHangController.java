package vn.ptit.restaurant.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.ptit.restaurant.common.catalog.AbstractCatalogController;
import vn.ptit.restaurant.dto.request.KhachHangRequest;
import vn.ptit.restaurant.dto.request.KhachHangSearchRequest;
import vn.ptit.restaurant.dto.response.KhachHangResponse;
import vn.ptit.restaurant.service.KhachHangService;

@RestController
@RequestMapping("/api/khach-hang")
public class KhachHangController extends AbstractCatalogController<String, KhachHangRequest, KhachHangRequest, KhachHangResponse, KhachHangSearchRequest> {

    public KhachHangController(KhachHangService service) {
        super(service);
    }
}
