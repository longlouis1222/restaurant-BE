package vn.ptit.restaurant.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.ptit.restaurant.common.catalog.AbstractCatalogController;
import vn.ptit.restaurant.dto.request.NhaCungCapRequest;
import vn.ptit.restaurant.dto.request.NhaCungCapSearchRequest;
import vn.ptit.restaurant.dto.response.NhaCungCapResponse;
import vn.ptit.restaurant.service.NhaCungCapService;

@RestController
@RequestMapping("/api/nha-cung-cap")
public class NhaCungCapController extends AbstractCatalogController<String, NhaCungCapRequest, NhaCungCapRequest, NhaCungCapResponse, NhaCungCapSearchRequest> {

    public NhaCungCapController(NhaCungCapService service) {
        super(service);
    }
}
