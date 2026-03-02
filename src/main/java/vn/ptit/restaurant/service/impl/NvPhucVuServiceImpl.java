package vn.ptit.restaurant.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.ptit.restaurant.common.catalog.AbstractCatalogService;
import vn.ptit.restaurant.dto.request.NvPhucVuRequest;
import vn.ptit.restaurant.dto.request.NvPhucVuSearchRequest;
import vn.ptit.restaurant.dto.response.NvPhucVuResponse;
import vn.ptit.restaurant.entity.NvPhucVu;
import vn.ptit.restaurant.exception.NotFoundException;
import vn.ptit.restaurant.repository.NvPhucVuRepository;
import vn.ptit.restaurant.repository.NhanVienRepository;
import vn.ptit.restaurant.service.NvPhucVuService;

@Service
@RequiredArgsConstructor
@Transactional
public class NvPhucVuServiceImpl extends AbstractCatalogService<
        NvPhucVu, String, NvPhucVuRequest, NvPhucVuRequest, NvPhucVuResponse, NvPhucVuSearchRequest>
        implements NvPhucVuService {

    private final NvPhucVuRepository repository;
    private final NhanVienRepository nhanVienRepository;

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
        if (!nhanVienRepository.existsById(id)) {
            throw new NotFoundException("NhanVien not found: " + id);
        }

        return NvPhucVu.builder()
                .maNhanVien(id)
                .nhanVien(nhanVienRepository.findById(id).orElse(null))
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
}
