package vn.ptit.restaurant.service;

import vn.ptit.restaurant.common.catalog.CatalogServiceContract;
import vn.ptit.restaurant.dto.request.NvPhucVuRequest;
import vn.ptit.restaurant.dto.request.NvPhucVuSearchRequest;
import vn.ptit.restaurant.dto.request.ThemMonRequest;
import vn.ptit.restaurant.dto.response.NvPhucVuResponse;
import vn.ptit.restaurant.dto.response.OrderResponse;
import vn.ptit.restaurant.dto.response.PageResponse;
import vn.ptit.restaurant.entity.NvPhucVu;

import java.time.LocalDateTime;
import java.util.List;

public interface NvPhucVuService extends CatalogServiceContract<
        NvPhucVu, String, NvPhucVuRequest, NvPhucVuRequest, NvPhucVuResponse, NvPhucVuSearchRequest> {

    // Tạo mới một order cho bàn ăn, gắn với nhân viên phục vụ và danh sách món
    OrderResponse taoOrder(String maBan, String maNhanVienPhucVu, List<ThemMonRequest> dsMon);

    // Cập nhật danh sách món trong order hiện có (thêm/sửa/xóa số lượng)
    OrderResponse capNhatOrder(String maHoaDon, List<ThemMonRequest> dsMon);

    // Gửi order hiện tại sang bếp, thiết lập thời gian đặt món
    OrderResponse guiOrderSangBep(String maHoaDon, String maNhanVienPhucVu);

    // Lấy danh sách order/món đã sẵn sàng để phục vụ cho một nhân viên phục vụ cụ thể
    PageResponse<OrderResponse> layDsOrderSanSangPhucVu(String maNhanVienPhucVu, int page, int size);

    // Xác nhận đã phục vụ xong món/bàn cho một order, cập nhật thời gian phục vụ
    OrderResponse xacNhanPhucVu(String maHoaDon, String maNhanVienPhucVu, LocalDateTime thoiGianPhucVu);

    // Yêu cầu thanh toán cho một order/hóa đơn, chuyển sang trạng thái chờ thanh toán
    OrderResponse yeuCauThanhToan(String maHoaDon, String maNhanVienPhucVu);

    // Tra cứu lịch sử order của nhân viên phục vụ theo khoảng thời gian và trạng thái
    PageResponse<OrderResponse> traCuuOrderTheoNhanVien(String maNhanVienPhucVu,
                                                        LocalDateTime thoiGianBatDau,
                                                        LocalDateTime thoiGianKetThuc,
                                                        String trangThai,
                                                        int page,
                                                        int size);
}
