package vn.ptit.restaurant.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.ptit.restaurant.common.catalog.AbstractCatalogService;
import vn.ptit.restaurant.dto.request.KhachHangRequest;
import vn.ptit.restaurant.dto.request.KhachHangSearchRequest;
import vn.ptit.restaurant.dto.response.KhachHangResponse;
import vn.ptit.restaurant.entity.KhachHang;
import vn.ptit.restaurant.repository.KhachHangRepository;
import vn.ptit.restaurant.service.KhachHangService;

@Service
@RequiredArgsConstructor
@Transactional
public class KhachHangServiceImpl extends AbstractCatalogService<
        KhachHang, String, KhachHangRequest, KhachHangRequest, KhachHangResponse, KhachHangSearchRequest>
        implements KhachHangService {

    private final KhachHangRepository repository;

    @Override
    protected JpaRepository<KhachHang, String> getRepository() {
        return repository;
    }

    @Override
    protected JpaSpecificationExecutor<KhachHang> getSpecRepository() {
        return repository;
    }

    @Override
    protected String generateIdIfNeeded(KhachHangRequest request) {
        if (request.getMaKhachHang() == null || request.getMaKhachHang().trim().isEmpty()) {
            return "KH-" + System.currentTimeMillis();
        }
        return request.getMaKhachHang();
    }

    @Override
    protected KhachHang createEntity(KhachHangRequest request, String id) {
        return KhachHang.builder()
                .maKhachHang(id)
                .tenKhachHang(request.getTenKhachHang())
                .sdt(request.getSdt())
                .diaChi(request.getDiaChi())
                .ngaySinh(request.getNgaySinh())
                .diemTichLuy(request.getDiemTichLuy())
                .build();
    }

    @Override
    protected void applyUpdate(KhachHang entity, KhachHangRequest request) {
        if (request.getTenKhachHang() != null) entity.setTenKhachHang(request.getTenKhachHang());
        if (request.getSdt() != null) entity.setSdt(request.getSdt());
        if (request.getDiaChi() != null) entity.setDiaChi(request.getDiaChi());
        if (request.getNgaySinh() != null) entity.setNgaySinh(request.getNgaySinh());
        if (request.getDiemTichLuy() != null) entity.setDiemTichLuy(request.getDiemTichLuy());
    }

    @Override
    protected KhachHangResponse toResponse(KhachHang entity) {
        return KhachHangResponse.builder()
                .maKhachHang(entity.getMaKhachHang())
                .tenKhachHang(entity.getTenKhachHang())
                .sdt(entity.getSdt())
                .diaChi(entity.getDiaChi())
                .ngaySinh(entity.getNgaySinh())
                .diemTichLuy(entity.getDiemTichLuy())
                .build();
    }

    @Override
    protected String[] getSearchFields() {
        return new String[]{"maKhachHang", "tenKhachHang"};
    }
}
