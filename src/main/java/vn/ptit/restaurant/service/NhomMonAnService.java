package vn.ptit.restaurant.service;

import vn.ptit.restaurant.dto.request.NhomMonAnRequest;
import vn.ptit.restaurant.dto.request.NhomMonAnSearchRequest;
import vn.ptit.restaurant.dto.response.NhomMonAnResponse;
import vn.ptit.restaurant.dto.response.PageResponse;
import vn.ptit.restaurant.entity.NhomMonAn;
import vn.ptit.restaurant.common.catalog.CatalogServiceContract;

import java.util.List;

public interface NhomMonAnService extends CatalogServiceContract<
        NhomMonAn, String, NhomMonAnRequest, NhomMonAnRequest, NhomMonAnResponse, NhomMonAnSearchRequest> {

    // no additional methods; inherits CRUD + search from CatalogServiceContract
}
