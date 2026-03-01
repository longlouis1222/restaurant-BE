package vn.ptit.restaurant.dto.request;

import vn.ptit.restaurant.common.catalog.BaseSearchRequest;

public class ChucVuSearchRequest extends BaseSearchRequest {
    public ChucVuSearchRequest() {
        this.setSortBy("maChucVu");
    }
}
