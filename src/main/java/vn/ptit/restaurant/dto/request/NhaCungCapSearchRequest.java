package vn.ptit.restaurant.dto.request;

import vn.ptit.restaurant.common.catalog.BaseSearchRequest;

public class NhaCungCapSearchRequest extends BaseSearchRequest {
    public NhaCungCapSearchRequest() {
        this.setSortBy("maNcc");
    }
}
