package vn.ptit.restaurant.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.ptit.restaurant.common.catalog.AbstractCatalogService;
import vn.ptit.restaurant.common.CodeGenerator;
import vn.ptit.restaurant.dto.request.NhomMonAnRequest;
import vn.ptit.restaurant.dto.request.NhomMonAnSearchRequest;
import vn.ptit.restaurant.dto.response.NhomMonAnResponse;
import vn.ptit.restaurant.entity.NhomMonAn;
import vn.ptit.restaurant.repository.NhomMonAnRepository;
import vn.ptit.restaurant.service.NhomMonAnService;

@Service
@RequiredArgsConstructor
@Transactional
public class NhomMonAnServiceImpl extends AbstractCatalogService<
        NhomMonAn, String, NhomMonAnRequest, NhomMonAnRequest, NhomMonAnResponse, NhomMonAnSearchRequest>
        implements NhomMonAnService
{

    private final NhomMonAnRepository repository;

    @Override
    protected JpaRepository<NhomMonAn, String> getRepository() {
        return repository;
    }

    @Override
    protected JpaSpecificationExecutor<NhomMonAn> getSpecRepository() {
        return repository;
    }

    @Override
    protected String generateIdIfNeeded(NhomMonAnRequest request) {
        if (request.getMaNhomMon() == null || request.getMaNhomMon().trim().isEmpty()) {
            return CodeGenerator.generateCode("NM", 10);
        }
        return request.getMaNhomMon();
    }

    @Override
    protected NhomMonAn createEntity(NhomMonAnRequest request, String id) {
        return NhomMonAn.builder()
                .maNhomMon(id)
                .tenNhomMon(request.getTenNhomMon())
                .moTa(request.getMoTa())
                .build();
    }

    @Override
    protected void applyUpdate(NhomMonAn entity, NhomMonAnRequest request) {
        if (request.getTenNhomMon() != null) entity.setTenNhomMon(request.getTenNhomMon());
        if (request.getMoTa() != null) entity.setMoTa(request.getMoTa());
    }

    @Override
    protected NhomMonAnResponse toResponse(NhomMonAn entity) {
        return NhomMonAnResponse.builder()
                .maNhomMon(entity.getMaNhomMon())
                .tenNhomMon(entity.getTenNhomMon())
                .moTa(entity.getMoTa())
                .build();
    }

    @Override
    protected String[] getSearchFields() {
        return new String[]{"maNhomMon", "tenNhomMon"};
    }
}
