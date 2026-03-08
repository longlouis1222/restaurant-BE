package vn.ptit.restaurant.service;

import vn.ptit.restaurant.common.catalog.CatalogServiceContract;
import vn.ptit.restaurant.dto.request.NvThuNganRequest;
import vn.ptit.restaurant.dto.request.NvThuNganSearchRequest;
import vn.ptit.restaurant.dto.response.NvThuNganResponse;
import vn.ptit.restaurant.dto.response.OrderResponse;
import vn.ptit.restaurant.dto.response.PageResponse;
import vn.ptit.restaurant.entity.NvThuNgan;

public interface NvThuNganService extends CatalogServiceContract<
        NvThuNgan, String, NvThuNganRequest, NvThuNganRequest, NvThuNganResponse, NvThuNganSearchRequest> {

    // Lấy danh sách hóa đơn/bàn đang chờ thanh toán
    PageResponse<OrderResponse> layDsHoaDonChoThanhToan(int page, int size);

    // Thực hiện thanh toán cho một hóa đơn, cập nhật thời gian thanh toán
    OrderResponse thanhToanHoaDon(String maHoaDon, String maNhanVienThuNgan);
}
