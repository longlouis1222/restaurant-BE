package vn.ptit.restaurant.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.ptit.restaurant.common.catalog.AbstractCatalogController;
import vn.ptit.restaurant.dto.request.NhanVienRequest;
import vn.ptit.restaurant.dto.request.NhanVienSearchRequest;
import vn.ptit.restaurant.dto.response.NhanVienResponse;
import vn.ptit.restaurant.service.NhanVienService;

@RestController
@RequestMapping("/api/nhan-vien")
public class NhanVienController extends AbstractCatalogController<String, NhanVienRequest, NhanVienRequest, NhanVienResponse, NhanVienSearchRequest> {

    public NhanVienController(NhanVienService service) {
        super(service);
    }
}
