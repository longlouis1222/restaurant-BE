package vn.ptit.restaurant.service;

import vn.ptit.restaurant.common.catalog.CatalogServiceContract;
import vn.ptit.restaurant.dto.request.MonAnRequest;
import vn.ptit.restaurant.dto.request.MonAnSearchRequest;
import vn.ptit.restaurant.dto.response.MonAnResponse;
import vn.ptit.restaurant.entity.MonAn;

public interface MonAnService extends CatalogServiceContract<
        MonAn, String, MonAnRequest, MonAnRequest, MonAnResponse, MonAnSearchRequest> {
}
