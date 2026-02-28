package vn.ptit.restaurant.service;

import vn.ptit.restaurant.dto.request.HoaDonSearchRequest;
import vn.ptit.restaurant.dto.request.TaoHoaDonRequest;
import vn.ptit.restaurant.dto.response.HoaDonResponse;
import vn.ptit.restaurant.dto.response.PageResponse;

public interface HoaDonService {

    HoaDonResponse taoHoaDon(TaoHoaDonRequest request);

    void thanhToan(String maHoaDon);

    PageResponse<HoaDonResponse> search(HoaDonSearchRequest request);
}