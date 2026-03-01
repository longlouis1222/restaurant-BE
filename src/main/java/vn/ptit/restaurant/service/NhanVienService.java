package vn.ptit.restaurant.service;

import vn.ptit.restaurant.common.catalog.CatalogServiceContract;
import vn.ptit.restaurant.dto.request.NhanVienRequest;
import vn.ptit.restaurant.dto.request.NhanVienSearchRequest;
import vn.ptit.restaurant.dto.response.NhanVienResponse;
import vn.ptit.restaurant.entity.NhanVien;

public interface NhanVienService extends CatalogServiceContract<
        NhanVien, String, NhanVienRequest, NhanVienRequest, NhanVienResponse, NhanVienSearchRequest> {
}
