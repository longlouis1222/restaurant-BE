package vn.ptit.restaurant.dto.request;

import vn.ptit.restaurant.common.catalog.BaseSearchRequest;

public class NguyenLieuSearchRequest extends BaseSearchRequest {
    public NguyenLieuSearchRequest() {
        this.setSortBy("maNguyenLieu");
    }
}
