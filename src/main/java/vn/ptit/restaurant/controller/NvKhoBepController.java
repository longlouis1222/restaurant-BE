package vn.ptit.restaurant.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.ptit.restaurant.common.catalog.AbstractCatalogController;
import vn.ptit.restaurant.dto.request.NvKhoBepRequest;
import vn.ptit.restaurant.dto.request.NvKhoBepSearchRequest;
import vn.ptit.restaurant.dto.response.NvKhoBepResponse;
import vn.ptit.restaurant.service.NvKhoBepService;

@RestController
@RequestMapping("/api/nv-kho-bep")
public class NvKhoBepController extends AbstractCatalogController<String, NvKhoBepRequest, NvKhoBepRequest, NvKhoBepResponse, NvKhoBepSearchRequest> {

    public NvKhoBepController(NvKhoBepService service) {
        super(service);
    }
}
