package vn.ptit.restaurant.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.ptit.restaurant.common.catalog.AbstractCatalogService;
import vn.ptit.restaurant.dto.request.NvThuNganRequest;
import vn.ptit.restaurant.dto.request.NvThuNganSearchRequest;
import vn.ptit.restaurant.dto.response.NvThuNganResponse;
import vn.ptit.restaurant.entity.NvThuNgan;
import vn.ptit.restaurant.exception.NotFoundException;
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
}
