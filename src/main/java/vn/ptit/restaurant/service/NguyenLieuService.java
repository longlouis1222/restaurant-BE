package vn.ptit.restaurant.service;

import vn.ptit.restaurant.common.catalog.CatalogServiceContract;
import vn.ptit.restaurant.dto.request.NguyenLieuRequest;
import vn.ptit.restaurant.dto.request.NguyenLieuSearchRequest;
import vn.ptit.restaurant.dto.response.NguyenLieuResponse;
import vn.ptit.restaurant.entity.NguyenLieu;

public interface NguyenLieuService extends CatalogServiceContract<
        NguyenLieu, String, NguyenLieuRequest, NguyenLieuRequest, NguyenLieuResponse, NguyenLieuSearchRequest> {
}
