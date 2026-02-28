package vn.ptit.restaurant.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.ptit.restaurant.common.catalog.AbstractCatalogController;
import vn.ptit.restaurant.dto.request.NhomMonAnRequest;
import vn.ptit.restaurant.dto.request.NhomMonAnSearchRequest;
import vn.ptit.restaurant.dto.response.NhomMonAnResponse;
import vn.ptit.restaurant.service.NhomMonAnService;

@RestController
@RequestMapping("/api/nhom-mon")
public class NhomMonAnController extends AbstractCatalogController<String, NhomMonAnRequest, NhomMonAnRequest, NhomMonAnResponse, NhomMonAnSearchRequest> {

    public NhomMonAnController(NhomMonAnService nhomMonAnService) {
        super(nhomMonAnService);
    }
}
