package vn.ptit.restaurant.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.ptit.restaurant.common.CodeGenerator;
import vn.ptit.restaurant.common.catalog.AbstractCatalogService;
import vn.ptit.restaurant.dto.request.NhaCungCapRequest;
import vn.ptit.restaurant.dto.request.NhaCungCapSearchRequest;
import vn.ptit.restaurant.dto.response.NhaCungCapResponse;
import vn.ptit.restaurant.entity.NhaCungCap;
import vn.ptit.restaurant.repository.NhaCungCapRepository;
import vn.ptit.restaurant.service.NhaCungCapService;

@Service
@RequiredArgsConstructor
@Transactional
public class NhaCungCapServiceImpl extends AbstractCatalogService<
        NhaCungCap, String, NhaCungCapRequest, NhaCungCapRequest, NhaCungCapResponse, NhaCungCapSearchRequest>
        implements NhaCungCapService {

    private final NhaCungCapRepository repository;

    @Override
    protected JpaRepository<NhaCungCap, String> getRepository() {
        return repository;
    }

    @Override
    protected JpaSpecificationExecutor<NhaCungCap> getSpecRepository() {
        return repository;
    }

    @Override
    protected String generateIdIfNeeded(NhaCungCapRequest request) {
        if (request.getMaNcc() == null || request.getMaNcc().trim().isEmpty()) {
            return CodeGenerator.generateCode("NCC", 10);
        }
        return request.getMaNcc();
    }

    @Override
    protected NhaCungCap createEntity(NhaCungCapRequest request, String id) {
        return NhaCungCap.builder()
                .maNcc(id)
                .tenNcc(request.getTenNcc())
                .thongTin(request.getThongTin())
                .build();
    }

    @Override
    protected void applyUpdate(NhaCungCap entity, NhaCungCapRequest request) {
        if (request.getTenNcc() != null) entity.setTenNcc(request.getTenNcc());
        if (request.getThongTin() != null) entity.setThongTin(request.getThongTin());
    }

    @Override
    protected NhaCungCapResponse toResponse(NhaCungCap entity) {
        return NhaCungCapResponse.builder()
                .maNcc(entity.getMaNcc())
                .tenNcc(entity.getTenNcc())
                .thongTin(entity.getThongTin())
                .build();
    }

    @Override
    protected String[] getSearchFields() {
        return new String[]{"maNcc", "tenNcc"};
    }
}
