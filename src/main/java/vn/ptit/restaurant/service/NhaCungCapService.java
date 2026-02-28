package vn.ptit.restaurant.service;

import vn.ptit.restaurant.common.catalog.CatalogServiceContract;
import vn.ptit.restaurant.dto.request.NhaCungCapRequest;
import vn.ptit.restaurant.dto.request.NhaCungCapSearchRequest;
import vn.ptit.restaurant.dto.response.NhaCungCapResponse;
import vn.ptit.restaurant.entity.NhaCungCap;

public interface NhaCungCapService extends CatalogServiceContract<
        NhaCungCap, String, NhaCungCapRequest, NhaCungCapRequest, NhaCungCapResponse, NhaCungCapSearchRequest> {
}
