package vn.ptit.restaurant.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.ptit.restaurant.common.catalog.AbstractCatalogController;
import vn.ptit.restaurant.dto.request.BanAnRequest;
import vn.ptit.restaurant.dto.request.BanAnSearchRequest;
import vn.ptit.restaurant.dto.response.BanAnResponse;
import vn.ptit.restaurant.service.BanAnService;

@RestController
@RequestMapping("/api/ban-an")
public class BanAnController extends AbstractCatalogController<String, BanAnRequest, BanAnRequest, BanAnResponse, BanAnSearchRequest> {

    public BanAnController(BanAnService service) {
        super(service);
    }
}
