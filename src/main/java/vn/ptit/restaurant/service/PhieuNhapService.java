package vn.ptit.restaurant.service;

import vn.ptit.restaurant.common.catalog.CatalogServiceContract;
import vn.ptit.restaurant.dto.request.PhieuNhapRequest;
import vn.ptit.restaurant.dto.request.PhieuNhapSearchRequest;
import vn.ptit.restaurant.dto.response.PhieuNhapResponse;
import vn.ptit.restaurant.entity.PhieuNhap;

public interface PhieuNhapService extends CatalogServiceContract<
        PhieuNhap, String, PhieuNhapRequest, PhieuNhapRequest, PhieuNhapResponse, PhieuNhapSearchRequest> {
}
