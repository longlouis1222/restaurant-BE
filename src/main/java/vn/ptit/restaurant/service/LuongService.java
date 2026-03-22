package vn.ptit.restaurant.service;

import vn.ptit.restaurant.common.catalog.CatalogServiceContract;
import vn.ptit.restaurant.dto.request.LuongRequest;
import vn.ptit.restaurant.dto.request.LuongSearchRequest;
import vn.ptit.restaurant.dto.response.LuongResponse;
import vn.ptit.restaurant.entity.Luong;

public interface LuongService extends CatalogServiceContract<
        Luong, String, LuongRequest, LuongRequest, LuongResponse, LuongSearchRequest> {
}
