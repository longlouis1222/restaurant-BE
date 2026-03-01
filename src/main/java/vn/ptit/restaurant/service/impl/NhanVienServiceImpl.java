package vn.ptit.restaurant.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.ptit.restaurant.common.catalog.AbstractCatalogService;
import vn.ptit.restaurant.dto.request.NhanVienRequest;
import vn.ptit.restaurant.dto.request.NhanVienSearchRequest;
import vn.ptit.restaurant.dto.response.NhanVienResponse;
import vn.ptit.restaurant.entity.NhanVien;
import vn.ptit.restaurant.entity.ChucVu;
import vn.ptit.restaurant.repository.NhanVienRepository;
import vn.ptit.restaurant.service.NhanVienService;

@Service
@RequiredArgsConstructor
@Transactional
public class NhanVienServiceImpl extends AbstractCatalogService<
        NhanVien, String, NhanVienRequest, NhanVienRequest, NhanVienResponse, NhanVienSearchRequest>
        implements NhanVienService {

    private final NhanVienRepository repository;

    @Override
    protected JpaRepository<NhanVien, String> getRepository() {
        return repository;
    }

    @Override
    protected JpaSpecificationExecutor<NhanVien> getSpecRepository() {
        return repository;
    }

    @Override
    protected String generateIdIfNeeded(NhanVienRequest request) {
        if (request.getMaNhanVien() == null || request.getMaNhanVien().trim().isEmpty()) {
            return "NV-" + System.currentTimeMillis();
        }
        return request.getMaNhanVien();
    }

    @Override
    protected NhanVien createEntity(NhanVienRequest request, String id) {
        return NhanVien.builder()
                .maNhanVien(id)
                .tenNhanVien(request.getTenNhanVien())
                .soDienThoai(request.getSoDienThoai())
                .ngaySinh(request.getNgaySinh())
                .ngayVaoLam(request.getNgayVaoLam())
                .cccd(request.getCccd())
                .chucVu(request.getMaChucVu() != null ? ChucVu.builder().maChucVu(request.getMaChucVu()).build() : null)
                .build();
    }

    @Override
    protected void applyUpdate(NhanVien entity, NhanVienRequest request) {
        if (request.getTenNhanVien() != null) entity.setTenNhanVien(request.getTenNhanVien());
        if (request.getSoDienThoai() != null) entity.setSoDienThoai(request.getSoDienThoai());
        if (request.getNgaySinh() != null) entity.setNgaySinh(request.getNgaySinh());
        if (request.getNgayVaoLam() != null) entity.setNgayVaoLam(request.getNgayVaoLam());
        if (request.getCccd() != null) entity.setCccd(request.getCccd());
        if (request.getMaChucVu() != null) entity.setChucVu(ChucVu.builder().maChucVu(request.getMaChucVu()).build());
    }

    @Override
    protected NhanVienResponse toResponse(NhanVien entity) {
        return NhanVienResponse.builder()
                .maNhanVien(entity.getMaNhanVien())
                .tenNhanVien(entity.getTenNhanVien())
                .soDienThoai(entity.getSoDienThoai())
                .ngaySinh(entity.getNgaySinh())
                .ngayVaoLam(entity.getNgayVaoLam())
                .cccd(entity.getCccd())
                .maChucVu(entity.getChucVu() != null ? entity.getChucVu().getMaChucVu() : null)
                .build();
    }

    @Override
    protected String[] getSearchFields() {
        return new String[]{"maNhanVien", "tenNhanVien"};
    }
}
