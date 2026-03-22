package vn.ptit.restaurant.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.ptit.restaurant.common.catalog.AbstractCatalogService;
import vn.ptit.restaurant.dto.request.NvPhucVuRequest;
import vn.ptit.restaurant.dto.request.NvPhucVuSearchRequest;
import vn.ptit.restaurant.dto.request.ThemMonRequest;
import vn.ptit.restaurant.dto.response.NvPhucVuResponse;
import vn.ptit.restaurant.dto.response.OrderResponse;
import vn.ptit.restaurant.dto.response.PageResponse;
import vn.ptit.restaurant.entity.*;
import vn.ptit.restaurant.entity.id.ChiTietHoaDonId;
import vn.ptit.restaurant.exception.NotFoundException;
import vn.ptit.restaurant.mapper.OrderMapper;
import vn.ptit.restaurant.repository.*;
import vn.ptit.restaurant.service.NvPhucVuService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class NvPhucVuServiceImpl extends AbstractCatalogService<
        NvPhucVu, String, NvPhucVuRequest, NvPhucVuRequest, NvPhucVuResponse, NvPhucVuSearchRequest>
        implements NvPhucVuService {

    private final NvPhucVuRepository repository;
    private final NhanVienRepository nhanVienRepository;
    private final HoaDonRepository hoaDonRepository;
    private final ChiTietHoaDonRepository chiTietHoaDonRepository;
    private final BanAnRepository banAnRepository;
    private final MonAnRepository monAnRepository;
    private final OrderMapper orderMapper;

    @Override
    protected JpaRepository<NvPhucVu, String> getRepository() {
        return repository;
    }

    @Override
    protected JpaSpecificationExecutor<NvPhucVu> getSpecRepository() {
        return repository;
    }

    @Override
    protected String generateIdIfNeeded(NvPhucVuRequest request) {
        return request.getMaNhanVien();
    }

    @Override
    protected NvPhucVu createEntity(NvPhucVuRequest request, String id) {
        // Validate NhanVien exists
        NhanVien parent = nhanVienRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("NhanVien not found: " + id));

        return NvPhucVu.builder()
                .nhanVien(parent)
                .khuVucPhuTrach(request.getKhuVucPhuTrach())
                .soLuongBanPhucVu(request.getSoLuongBanPhucVu())
                .build();
    }

    @Override
    protected void applyUpdate(NvPhucVu entity, NvPhucVuRequest request) {
        if (request.getKhuVucPhuTrach() != null) entity.setKhuVucPhuTrach(request.getKhuVucPhuTrach());
        if (request.getSoLuongBanPhucVu() != null) entity.setSoLuongBanPhucVu(request.getSoLuongBanPhucVu());
    }

    @Override
    protected NvPhucVuResponse toResponse(NvPhucVu entity) {
        return NvPhucVuResponse.builder()
                .maNhanVien(entity.getMaNhanVien())
                .khuVucPhuTrach(entity.getKhuVucPhuTrach())
                .soLuongBanPhucVu(entity.getSoLuongBanPhucVu())
                .build();
    }

    @Override
    protected String[] getSearchFields() {
        return new String[]{"maNhanVien", "khuVucPhuTrach"};
    }

    // ========== ORDER LOGIC ==========

    @Override
    public OrderResponse taoOrder(String maBan, String maNhanVienPhucVu, List<ThemMonRequest> dsMon) {
        BanAn banAn = banAnRepository.findById(maBan)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy bàn: " + maBan));

        NhanVien nhanVien = nhanVienRepository.findById(maNhanVienPhucVu)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy nhân viên: " + maNhanVienPhucVu));

        String maHoaDon = UUID.randomUUID().toString();

        HoaDon hoaDon = HoaDon.builder()
                .maHoaDon(maHoaDon)
                .banAn(banAn)
                .nhanVien(nhanVien)
                .ngayLap(LocalDateTime.now())
                .tongTien(BigDecimal.ZERO)
                .trangThai(HoaDon.TrangThaiHoaDon.NEW)
                .build();
        hoaDonRepository.save(hoaDon);

        BigDecimal tongTien = BigDecimal.ZERO;
        for (ThemMonRequest item : dsMon) {
            MonAn monAn = monAnRepository.findById(item.getMaMon())
                    .orElseThrow(() -> new NotFoundException("Không tìm thấy món: " + item.getMaMon()));
            BigDecimal donGia = monAn.getDonGia();
            Integer soLuong = item.getSoLuong();
            BigDecimal thanhTien = donGia.multiply(BigDecimal.valueOf(soLuong));
            tongTien = tongTien.add(thanhTien);

            ChiTietHoaDon chiTiet = ChiTietHoaDon.builder()
                    .id(new ChiTietHoaDonId(maHoaDon, monAn.getMaMon()))
                    .hoaDon(hoaDon)
                    .monAn(monAn)
                    .soLuong(soLuong)
                    .donGia(donGia)
                    .build();
            chiTietHoaDonRepository.save(chiTiet);
        }
        hoaDon.setTongTien(tongTien);
        hoaDonRepository.save(hoaDon);

        List<ChiTietHoaDon> chiTietList = chiTietHoaDonRepository.findAll(); // TODO: filter theo maHoaDon
        return orderMapper.toOrderResponse(hoaDon, chiTietList);
    }

    @Override
    public OrderResponse capNhatOrder(String maHoaDon, List<ThemMonRequest> dsMon) {
        HoaDon hoaDon = hoaDonRepository.findById(maHoaDon)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy hóa đơn: " + maHoaDon));

        // TODO: chỉ xóa chi tiết của hóa đơn này
        chiTietHoaDonRepository.deleteAll(chiTietHoaDonRepository.findAll());

        BigDecimal tongTien = BigDecimal.ZERO;
        for (ThemMonRequest item : dsMon) {
            MonAn monAn = monAnRepository.findById(item.getMaMon())
                    .orElseThrow(() -> new NotFoundException("Không tìm thấy món: " + item.getMaMon()));
            BigDecimal donGia = monAn.getDonGia();
            Integer soLuong = item.getSoLuong();
            BigDecimal thanhTien = donGia.multiply(BigDecimal.valueOf(soLuong));
            tongTien = tongTien.add(thanhTien);

            ChiTietHoaDon chiTiet = ChiTietHoaDon.builder()
                    .id(new ChiTietHoaDonId(maHoaDon, monAn.getMaMon()))
                    .hoaDon(hoaDon)
                    .monAn(monAn)
                    .soLuong(soLuong)
                    .donGia(donGia)
                    .build();
            chiTietHoaDonRepository.save(chiTiet);
        }
        hoaDon.setTongTien(tongTien);
        hoaDonRepository.save(hoaDon);

        List<ChiTietHoaDon> chiTietList = chiTietHoaDonRepository.findAll(); // TODO: filter theo maHoaDon
        return orderMapper.toOrderResponse(hoaDon, chiTietList);
    }

    @Override
    public OrderResponse guiOrderSangBep(String maHoaDon, String maNhanVienPhucVu) {
        HoaDon hoaDon = hoaDonRepository.findById(maHoaDon)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy hóa đơn: " + maHoaDon));

        nhanVienRepository.findById(maNhanVienPhucVu)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy nhân viên: " + maNhanVienPhucVu));

        if (hoaDon.getNgayLap() == null) {
            hoaDon.setNgayLap(LocalDateTime.now());
        }
        hoaDonRepository.save(hoaDon);

        List<ChiTietHoaDon> chiTietList = chiTietHoaDonRepository.findAll(); // TODO: filter theo maHoaDon
        return orderMapper.toOrderResponse(hoaDon, chiTietList);
    }

    @Override
    public PageResponse<OrderResponse> layDsOrderSanSangPhucVu(String maNhanVienPhucVu, int page, int size) {
        // Skeleton: tạm thời trả về rỗng
        return PageResponse.<OrderResponse>builder()
                .content(Collections.emptyList())
                .page(page)
                .size(size)
                .totalElements(0L)
                .totalPages(0)
                .build();
    }

    @Override
    public OrderResponse xacNhanPhucVu(String maHoaDon, String maNhanVienPhucVu, LocalDateTime thoiGianPhucVu) {
        HoaDon hoaDon = hoaDonRepository.findById(maHoaDon)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy hóa đơn: " + maHoaDon));
        if (thoiGianPhucVu == null) {
            thoiGianPhucVu = LocalDateTime.now();
        }
        List<ChiTietHoaDon> chiTietList = chiTietHoaDonRepository.findAll(); // TODO: filter theo maHoaDon
        OrderResponse response = orderMapper.toOrderResponse(hoaDon, chiTietList);
        response.setThoiGianPhucVu(thoiGianPhucVu);
        return response;
    }

    @Override
    public OrderResponse yeuCauThanhToan(String maHoaDon, String maNhanVienPhucVu) {
        HoaDon hoaDon = hoaDonRepository.findById(maHoaDon)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy hóa đơn: " + maHoaDon));
        List<ChiTietHoaDon> chiTietList = chiTietHoaDonRepository.findAll(); // TODO: filter theo maHoaDon
        return orderMapper.toOrderResponse(hoaDon, chiTietList);
    }

    @Override
    public PageResponse<OrderResponse> traCuuOrderTheoNhanVien(String maNhanVienPhucVu,
                                                               LocalDateTime thoiGianBatDau,
                                                               LocalDateTime thoiGianKetThuc,
                                                               String trangThai,
                                                               int page,
                                                               int size) {
        // Skeleton: tạm thời trả về rỗng
        return PageResponse.<OrderResponse>builder()
                .content(Collections.emptyList())
                .page(page)
                .size(size)
                .totalElements(0L)
                .totalPages(0)
                .build();
    }
}
