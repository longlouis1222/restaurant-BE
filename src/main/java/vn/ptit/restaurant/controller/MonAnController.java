package vn.ptit.restaurant.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.ptit.restaurant.common.catalog.AbstractCatalogController;
import vn.ptit.restaurant.dto.request.MonAnRequest;
import vn.ptit.restaurant.dto.request.MonAnSearchRequest;
import vn.ptit.restaurant.dto.response.MonAnResponse;
import vn.ptit.restaurant.service.MonAnService;

@RestController
@RequestMapping("/api/mon-an")
public class MonAnController extends AbstractCatalogController<String, MonAnRequest, MonAnRequest, MonAnResponse, MonAnSearchRequest> {

    public MonAnController(MonAnService service) {
        super(service);
    }
}
