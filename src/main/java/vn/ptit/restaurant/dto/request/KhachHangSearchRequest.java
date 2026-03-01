package vn.ptit.restaurant.dto.request;

import vn.ptit.restaurant.common.catalog.BaseSearchRequest;

public class KhachHangSearchRequest extends BaseSearchRequest {
    public KhachHangSearchRequest() {
        this.setSortBy("maKhachHang");
    }
}
