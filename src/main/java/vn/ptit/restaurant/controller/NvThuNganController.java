package vn.ptit.restaurant.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.ptit.restaurant.common.catalog.AbstractCatalogController;
import vn.ptit.restaurant.dto.request.NvThuNganRequest;
import vn.ptit.restaurant.dto.request.NvThuNganSearchRequest;
import vn.ptit.restaurant.dto.response.NvThuNganResponse;
import vn.ptit.restaurant.service.NvThuNganService;

@RestController
@RequestMapping("/api/nv-thu-ngan")
public class NvThuNganController extends AbstractCatalogController<String, NvThuNganRequest, NvThuNganRequest, NvThuNganResponse, NvThuNganSearchRequest> {

    public NvThuNganController(NvThuNganService service) {
        super(service);
    }
}
