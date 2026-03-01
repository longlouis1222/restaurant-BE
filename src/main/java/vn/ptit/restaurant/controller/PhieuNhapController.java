package vn.ptit.restaurant.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.ptit.restaurant.common.catalog.AbstractCatalogController;
import vn.ptit.restaurant.dto.request.PhieuNhapRequest;
import vn.ptit.restaurant.dto.request.PhieuNhapSearchRequest;
import vn.ptit.restaurant.dto.response.PhieuNhapResponse;
import vn.ptit.restaurant.service.PhieuNhapService;

@RestController
@RequestMapping("/api/phieu-nhap")
public class PhieuNhapController extends AbstractCatalogController<String, PhieuNhapRequest, PhieuNhapRequest, PhieuNhapResponse, PhieuNhapSearchRequest> {

    public PhieuNhapController(PhieuNhapService service) {
        super(service);
    }
}
