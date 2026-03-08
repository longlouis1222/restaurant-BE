package vn.ptit.restaurant.mapper;

import org.springframework.stereotype.Component;
import vn.ptit.restaurant.dto.response.OrderItemResponse;
import vn.ptit.restaurant.dto.response.OrderResponse;
import vn.ptit.restaurant.entity.BanAn;
import vn.ptit.restaurant.entity.ChiTietHoaDon;
import vn.ptit.restaurant.entity.HoaDon;
import vn.ptit.restaurant.entity.MonAn;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Mapper dùng chung để chuyển đổi giữa entity HoaDon/ChiTietHoaDon và DTO OrderResponse.
 */
@Component
public class OrderMapper {

    public OrderResponse toOrderResponse(HoaDon hoaDon, List<ChiTietHoaDon> chiTietList) {
        if (hoaDon == null) {
            return null;
        }

        return OrderResponse.builder()
                .maHoaDon(hoaDon.getMaHoaDon())
                .maBan(extractMaBan(hoaDon.getBanAn()))
                .maKhachHang(hoaDon.getKhachHang() != null ? hoaDon.getKhachHang().getMaKhachHang() : null)
                .maNhanVienPhucVu(hoaDon.getNhanVien() != null ? hoaDon.getNhanVien().getMaNhanVien() : null)
                .tongTien(hoaDon.getTongTien())
                .trangThaiHoaDon(hoaDon.getTrangThai() != null ? hoaDon.getTrangThai().name() : null)
                .thoiGianDatMon(hoaDon.getNgayLap())
                .thoiGianHoanThanhMon(null)
                .thoiGianPhucVu(null)
                .thoiGianThanhToan(null)
                .dsMon(toOrderItemResponses(chiTietList))
                .build();
    }

    public List<OrderResponse> toOrderResponseList(List<HoaDon> hoaDonList,
                                                   Map<String, List<ChiTietHoaDon>> chiTietByHoaDon) {
        if (hoaDonList == null) {
            return Collections.emptyList();
        }
        return hoaDonList.stream()
                .map(hd -> toOrderResponse(hd, chiTietByHoaDon.get(hd.getMaHoaDon())))
                .collect(Collectors.toList());
    }

    public List<OrderItemResponse> toOrderItemResponses(List<ChiTietHoaDon> chiTietList) {
        if (chiTietList == null) {
            return Collections.emptyList();
        }
        return chiTietList.stream()
                .map(this::toOrderItemResponse)
                .collect(Collectors.toList());
    }

    public OrderItemResponse toOrderItemResponse(ChiTietHoaDon ct) {
        if (ct == null) {
            return null;
        }

        MonAn monAn = ct.getMonAn();
        BigDecimal donGia = ct.getDonGia();
        Integer soLuong = ct.getSoLuong();

        BigDecimal thanhTien = (donGia != null && soLuong != null)
                ? donGia.multiply(BigDecimal.valueOf(soLuong))
                : BigDecimal.ZERO;

        return OrderItemResponse.builder()
                .maMon(monAn != null ? monAn.getMaMon() : null)
                .tenMon(monAn != null ? monAn.getTenMon() : null)
                .soLuong(soLuong)
                .donGia(donGia)
                .thanhTien(thanhTien)
                .thoiGianHoanThanhMon(null)
                .build();
    }

    private String extractMaBan(BanAn banAn) {
        return banAn != null ? banAn.getMaBan() : null;
    }
}
