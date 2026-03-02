package vn.ptit.restaurant.service;

import vn.ptit.restaurant.common.catalog.CatalogServiceContract;
import vn.ptit.restaurant.dto.request.NvPhucVuRequest;
import vn.ptit.restaurant.dto.request.NvPhucVuSearchRequest;
import vn.ptit.restaurant.dto.response.NvPhucVuResponse;
import vn.ptit.restaurant.entity.NvPhucVu;

public interface NvPhucVuService extends CatalogServiceContract<
        NvPhucVu, String, NvPhucVuRequest, NvPhucVuRequest, NvPhucVuResponse, NvPhucVuSearchRequest> {
}
