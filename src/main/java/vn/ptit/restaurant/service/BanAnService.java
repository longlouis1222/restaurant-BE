package vn.ptit.restaurant.service;

import vn.ptit.restaurant.common.catalog.CatalogServiceContract;
import vn.ptit.restaurant.dto.request.BanAnRequest;
import vn.ptit.restaurant.dto.request.BanAnSearchRequest;
import vn.ptit.restaurant.dto.response.BanAnResponse;
import vn.ptit.restaurant.entity.BanAn;

public interface BanAnService extends CatalogServiceContract<
        BanAn, String, BanAnRequest, BanAnRequest, BanAnResponse, BanAnSearchRequest> {
}
