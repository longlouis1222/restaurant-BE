package vn.ptit.restaurant.dto.request;

import vn.ptit.restaurant.common.catalog.BaseSearchRequest;

public class NhanVienSearchRequest extends BaseSearchRequest {
    private String maChucVu;

    public NhanVienSearchRequest() {
        this.setSortBy("maNhanVien");
    }

    public String getMaChucVu() {
        return maChucVu;
    }

    public void setMaChucVu(String maChucVu) {
        this.maChucVu = maChucVu;
    }
}
