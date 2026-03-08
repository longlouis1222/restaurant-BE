package vn.ptit.restaurant.service.impl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.ptit.restaurant.common.catalog.AbstractCatalogService;
import vn.ptit.restaurant.dto.request.NvThuNganRequest;
import vn.ptit.restaurant.dto.request.NvThuNganSearchRequest;
import vn.ptit.restaurant.dto.response.NvThuNganResponse;
import vn.ptit.restaurant.dto.response.OrderResponse;
import vn.ptit.restaurant.dto.response.PageResponse;
import vn.ptit.restaurant.entity.HoaDon;
import vn.ptit.restaurant.entity.NvThuNgan;
import vn.ptit.restaurant.exception.NotFoundException;
import vn.ptit.restaurant.mapper.OrderMapper;
import vn.ptit.restaurant.repository.HoaDonRepository;
import vn.ptit.restaurant.repository.NvThuNganRepository;
import vn.ptit.restaurant.repository.NhanVienRepository;
import vn.ptit.restaurant.service.NvThuNganService;

@Service
@RequiredArgsConstructor
@Transactional
public class NvThuNganServiceImpl extends AbstractCatalogService<
        NvThuNgan, String, NvThuNganRequest, NvThuNganRequest, NvThuNganResponse, NvThuNganSearchRequest>
        implements NvThuNganService {

    private final NvThuNganRepository repository;
    private final NhanVienRepository nhanVienRepository;
    private final HoaDonRepository hoaDonRepository;
    private final OrderMapper orderMapper;

    @Override
    protected JpaRepository<NvThuNgan, String> getRepository() {
        return repository;
    }

    @Override
    protected JpaSpecificationExecutor<NvThuNgan> getSpecRepository() {
        return repository;
    }

    @Override
    protected String generateIdIfNeeded(NvThuNganRequest request) {
        return request.getMaNhanVien();
    }

    @Override
    protected NvThuNgan createEntity(NvThuNganRequest request, String id) {
        // Validate NhanVien exists
        if (!nhanVienRepository.existsById(id)) {
            throw new NotFoundException("NhanVien not found: " + id);
        }

        return NvThuNgan.builder()
                .maNhanVien(id)
                .nhanVien(nhanVienRepository.findById(id).orElse(null))
                .caThuNgan(request.getCaThuNgan())
                .tongTienXuLy(request.getTongTienXuLy())
                .build();
    }

    @Override
    protected void applyUpdate(NvThuNgan entity, NvThuNganRequest request) {
        if (request.getCaThuNgan() != null) entity.setCaThuNgan(request.getCaThuNgan());
        if (request.getTongTienXuLy() != null) entity.setTongTienXuLy(request.getTongTienXuLy());
    }

    @Override
    protected NvThuNganResponse toResponse(NvThuNgan entity) {
        return NvThuNganResponse.builder()
                .maNhanVien(entity.getMaNhanVien())
                .caThuNgan(entity.getCaThuNgan())
                .tongTienXuLy(entity.getTongTienXuLy())
                .build();
    }

    @Override
    protected String[] getSearchFields() {
        return new String[]{"maNhanVien", "caThuNgan"};
    }

    @Override
    public PageResponse<OrderResponse> layDsHoaDonChoThanhToan(int page, int size) {
        // Skeleton: tạm thời lấy tất cả hóa đơn NEW làm danh sách chờ thanh toán
        List<HoaDon> hoaDonList = hoaDonRepository.findAll();
        List<OrderResponse> content = hoaDonList.stream()
                .map(hd -> orderMapper.toOrderResponse(hd, Collections.emptyList()))
                .collect(Collectors.toList());

        return PageResponse.<OrderResponse>builder()
                .content(content)
                .page(page)
                .size(size)
                .totalElements((long) content.size())
                .totalPages(1)
                .build();
    }

    @Override
    public OrderResponse thanhToanHoaDon(String maHoaDon, String maNhanVienThuNgan) {
        HoaDon hoaDon = hoaDonRepository.findById(maHoaDon)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy hóa đơn: " + maHoaDon));

        // Đảm bảo nhân viên thu ngân tồn tại
        nhanVienRepository.findById(maNhanVienThuNgan)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy nhân viên thu ngân: " + maNhanVienThuNgan));

        // Cập nhật trạng thái hóa đơn là PAID và thời gian thanh toán (dùng ngayLap hoặc cột mới sau này)
        hoaDon.setTrangThai(HoaDon.TrangThaiHoaDon.PAID);
        if (hoaDon.getNgayLap() == null) {
            hoaDon.setNgayLap(LocalDateTime.now());
        }
        hoaDonRepository.save(hoaDon);

        OrderResponse response = orderMapper.toOrderResponse(hoaDon, Collections.emptyList());
        response.setThoiGianThanhToan(LocalDateTime.now());
        return response;
    }
}
