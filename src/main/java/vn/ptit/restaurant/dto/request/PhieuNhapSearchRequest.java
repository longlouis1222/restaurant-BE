package vn.ptit.restaurant.dto.request;

import vn.ptit.restaurant.common.catalog.BaseSearchRequest;

public class PhieuNhapSearchRequest extends BaseSearchRequest {
    private String maNcc;
    private String maNhanVien;

    public PhieuNhapSearchRequest() {
        this.setSortBy("maPhieuNhap");
    }

    public String getMaNcc() {
        return maNcc;
    }

    public void setMaNcc(String maNcc) {
        this.maNcc = maNcc;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }
}
