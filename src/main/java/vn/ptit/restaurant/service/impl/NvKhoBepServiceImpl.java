package vn.ptit.restaurant.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.ptit.restaurant.common.catalog.AbstractCatalogService;
import vn.ptit.restaurant.dto.request.NvKhoBepRequest;
import vn.ptit.restaurant.dto.request.NvKhoBepSearchRequest;
import vn.ptit.restaurant.dto.response.NvKhoBepResponse;
import vn.ptit.restaurant.entity.NvKhoBep;
import vn.ptit.restaurant.exception.NotFoundException;
import vn.ptit.restaurant.repository.NvKhoBepRepository;
import vn.ptit.restaurant.repository.NhanVienRepository;
import vn.ptit.restaurant.service.NvKhoBepService;

@Service
@RequiredArgsConstructor
@Transactional
public class NvKhoBepServiceImpl extends AbstractCatalogService<
        NvKhoBep, String, NvKhoBepRequest, NvKhoBepRequest, NvKhoBepResponse, NvKhoBepSearchRequest>
        implements NvKhoBepService {

    private final NvKhoBepRepository repository;
    private final NhanVienRepository nhanVienRepository;

    @Override
    protected JpaRepository<NvKhoBep, String> getRepository() {
        return repository;
    }

    @Override
    protected JpaSpecificationExecutor<NvKhoBep> getSpecRepository() {
        return repository;
    }

    @Override
    protected String generateIdIfNeeded(NvKhoBepRequest request) {
        return request.getMaNhanVien();
    }

    @Override
    protected NvKhoBep createEntity(NvKhoBepRequest request, String id) {
        // Validate NhanVien exists
        if (!nhanVienRepository.existsById(id)) {
            throw new NotFoundException("NhanVien not found: " + id);
        }

        return NvKhoBep.builder()
                .maNhanVien(id)
                .nhanVien(nhanVienRepository.findById(id).orElse(null))
                .viTri(request.getViTri())
                .trinhDo(request.getTrinhDo())
                .build();
    }

    @Override
    protected void applyUpdate(NvKhoBep entity, NvKhoBepRequest request) {
        if (request.getViTri() != null) entity.setViTri(request.getViTri());
        if (request.getTrinhDo() != null) entity.setTrinhDo(request.getTrinhDo());
    }

    @Override
    protected NvKhoBepResponse toResponse(NvKhoBep entity) {
        return NvKhoBepResponse.builder()
                .maNhanVien(entity.getMaNhanVien())
                .viTri(entity.getViTri())
                .trinhDo(entity.getTrinhDo())
                .build();
    }

    @Override
    protected String[] getSearchFields() {
        return new String[]{"maNhanVien", "viTri", "trinhDo"};
    }
}
