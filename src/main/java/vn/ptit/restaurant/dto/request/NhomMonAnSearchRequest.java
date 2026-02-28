package vn.ptit.restaurant.dto.request;

import vn.ptit.restaurant.common.catalog.BaseSearchRequest;

public class NhomMonAnSearchRequest extends BaseSearchRequest {
    // set default sortBy to maNhomMon to match entity field
    public NhomMonAnSearchRequest() {
        this.setSortBy("maNhomMon");
    }
}
