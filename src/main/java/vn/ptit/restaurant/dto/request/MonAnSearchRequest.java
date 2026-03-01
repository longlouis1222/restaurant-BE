package vn.ptit.restaurant.dto.request;

import vn.ptit.restaurant.common.catalog.BaseSearchRequest;

public class MonAnSearchRequest extends BaseSearchRequest {
    public MonAnSearchRequest() {
        this.setSortBy("maMon");
    }
}
