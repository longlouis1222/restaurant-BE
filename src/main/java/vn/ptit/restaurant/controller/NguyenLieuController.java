package vn.ptit.restaurant.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.ptit.restaurant.common.catalog.AbstractCatalogController;
import vn.ptit.restaurant.dto.request.NguyenLieuRequest;
import vn.ptit.restaurant.dto.request.NguyenLieuSearchRequest;
import vn.ptit.restaurant.dto.response.NguyenLieuResponse;
import vn.ptit.restaurant.service.NguyenLieuService;

@RestController
@RequestMapping("/api/nguyen-lieu")
public class NguyenLieuController extends AbstractCatalogController<String, NguyenLieuRequest, NguyenLieuRequest, NguyenLieuResponse, NguyenLieuSearchRequest> {

    public NguyenLieuController(NguyenLieuService service) {
        super(service);
    }
}
