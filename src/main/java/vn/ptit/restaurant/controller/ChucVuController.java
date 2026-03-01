package vn.ptit.restaurant.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.ptit.restaurant.common.catalog.AbstractCatalogController;
import vn.ptit.restaurant.dto.request.ChucVuRequest;
import vn.ptit.restaurant.dto.request.ChucVuSearchRequest;
import vn.ptit.restaurant.dto.response.ChucVuResponse;
import vn.ptit.restaurant.service.ChucVuService;

@RestController
@RequestMapping("/api/chuc-vu")
public class ChucVuController extends AbstractCatalogController<String, ChucVuRequest, ChucVuRequest, ChucVuResponse, ChucVuSearchRequest> {

    public ChucVuController(ChucVuService service) {
        super(service);
    }
}
