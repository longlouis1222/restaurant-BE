package vn.ptit.restaurant.dto.request;

import vn.ptit.restaurant.common.catalog.BaseSearchRequest;

public class BanAnSearchRequest extends BaseSearchRequest {
    public BanAnSearchRequest() {
        this.setSortBy("maBan");
    }
}
