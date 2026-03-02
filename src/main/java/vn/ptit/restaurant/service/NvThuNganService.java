package vn.ptit.restaurant.service;

import vn.ptit.restaurant.common.catalog.CatalogServiceContract;
import vn.ptit.restaurant.dto.request.NvThuNganRequest;
import vn.ptit.restaurant.dto.request.NvThuNganSearchRequest;
import vn.ptit.restaurant.dto.response.NvThuNganResponse;
import vn.ptit.restaurant.entity.NvThuNgan;

public interface NvThuNganService extends CatalogServiceContract<
        NvThuNgan, String, NvThuNganRequest, NvThuNganRequest, NvThuNganResponse, NvThuNganSearchRequest> {
}
