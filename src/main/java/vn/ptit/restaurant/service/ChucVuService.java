package vn.ptit.restaurant.service;

import vn.ptit.restaurant.common.catalog.CatalogServiceContract;
import vn.ptit.restaurant.dto.request.ChucVuRequest;
import vn.ptit.restaurant.dto.request.ChucVuSearchRequest;
import vn.ptit.restaurant.dto.response.ChucVuResponse;
import vn.ptit.restaurant.entity.ChucVu;

public interface ChucVuService extends CatalogServiceContract<
        ChucVu, String, ChucVuRequest, ChucVuRequest, ChucVuResponse, ChucVuSearchRequest> {
}
