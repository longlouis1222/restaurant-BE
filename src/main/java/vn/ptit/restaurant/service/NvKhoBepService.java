package vn.ptit.restaurant.service;

import vn.ptit.restaurant.common.catalog.CatalogServiceContract;
import vn.ptit.restaurant.dto.request.NvKhoBepRequest;
import vn.ptit.restaurant.dto.request.NvKhoBepSearchRequest;
import vn.ptit.restaurant.dto.response.NvKhoBepResponse;
import vn.ptit.restaurant.entity.NvKhoBep;

public interface NvKhoBepService extends CatalogServiceContract<
        NvKhoBep, String, NvKhoBepRequest, NvKhoBepRequest, NvKhoBepResponse, NvKhoBepSearchRequest> {
}
