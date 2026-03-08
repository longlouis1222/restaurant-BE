package vn.ptit.restaurant.service;

import vn.ptit.restaurant.common.catalog.CatalogServiceContract;
import vn.ptit.restaurant.dto.request.NvKhoBepRequest;
import vn.ptit.restaurant.dto.request.NvKhoBepSearchRequest;
import vn.ptit.restaurant.dto.response.NvKhoBepResponse;
import vn.ptit.restaurant.dto.response.OrderResponse;
import vn.ptit.restaurant.dto.response.PageResponse;
import vn.ptit.restaurant.entity.NvKhoBep;

public interface NvKhoBepService extends CatalogServiceContract<
        NvKhoBep, String, NvKhoBepRequest, NvKhoBepRequest, NvKhoBepResponse, NvKhoBepSearchRequest> {

    // Lấy danh sách order/món đang chờ bếp chế biến
    PageResponse<OrderResponse> layDsOrderChoCheBien(int page, int size);

    // Bếp xác nhận hoàn thành toàn bộ món của một order
    OrderResponse hoanThanhOrder(String maHoaDon, String maNhanVienBep);
}
