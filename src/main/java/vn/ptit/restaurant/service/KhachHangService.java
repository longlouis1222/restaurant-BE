package vn.ptit.restaurant.service;

import vn.ptit.restaurant.common.catalog.CatalogServiceContract;
import vn.ptit.restaurant.dto.request.KhachHangRequest;
import vn.ptit.restaurant.dto.request.KhachHangSearchRequest;
import vn.ptit.restaurant.dto.response.KhachHangResponse;
import vn.ptit.restaurant.entity.KhachHang;

public interface KhachHangService extends CatalogServiceContract<
        KhachHang, String, KhachHangRequest, KhachHangRequest, KhachHangResponse, KhachHangSearchRequest> {
}
