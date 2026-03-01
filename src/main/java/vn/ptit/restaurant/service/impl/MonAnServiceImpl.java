package vn.ptit.restaurant.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.ptit.restaurant.common.catalog.AbstractCatalogService;
import vn.ptit.restaurant.dto.request.MonAnRequest;
import vn.ptit.restaurant.dto.request.MonAnSearchRequest;
import vn.ptit.restaurant.dto.response.MonAnResponse;
import vn.ptit.restaurant.entity.MonAn;
import vn.ptit.restaurant.repository.MonAnRepository;
import vn.ptit.restaurant.service.MonAnService;

@Service
@RequiredArgsConstructor
@Transactional
public class MonAnServiceImpl extends AbstractCatalogService<
        MonAn, String, MonAnRequest, MonAnRequest, MonAnResponse, MonAnSearchRequest>
        implements MonAnService {

    private final MonAnRepository repository;

    @Override
    protected JpaRepository<MonAn, String> getRepository() {
        return repository;
    }

    @Override
    protected JpaSpecificationExecutor<MonAn> getSpecRepository() {
        return repository;
    }

    @Override
    protected String generateIdIfNeeded(MonAnRequest request) {
        if (request.getMaMon() == null || request.getMaMon().trim().isEmpty()) {
            return "MA-" + System.currentTimeMillis();
        }
        return request.getMaMon();
    }

    @Override
    protected MonAn createEntity(MonAnRequest request, String id) {
        return MonAn.builder()
                .maMon(id)
                .tenMon(request.getTenMon())
                .donGia(request.getDonGia())
                .thoiGianCheBien(request.getThoiGianCheBien())
                .nhomMonAn(request.getMaNhomMon() != null ? vn.ptit.restaurant.entity.NhomMonAn.builder().maNhomMon(request.getMaNhomMon()).build() : null)
                .build();
    }

    @Override
    protected void applyUpdate(MonAn entity, MonAnRequest request) {
        if (request.getTenMon() != null) entity.setTenMon(request.getTenMon());
        if (request.getDonGia() != null) entity.setDonGia(request.getDonGia());
        if (request.getThoiGianCheBien() != null) entity.setThoiGianCheBien(request.getThoiGianCheBien());
        if (request.getMaNhomMon() != null) entity.setNhomMonAn(vn.ptit.restaurant.entity.NhomMonAn.builder().maNhomMon(request.getMaNhomMon()).build());
    }

    @Override
    protected MonAnResponse toResponse(MonAn entity) {
        return MonAnResponse.builder()
                .maMon(entity.getMaMon())
                .tenMon(entity.getTenMon())
                .donGia(entity.getDonGia())
                .thoiGianCheBien(entity.getThoiGianCheBien())
                .maNhomMon(entity.getNhomMonAn() != null ? entity.getNhomMonAn().getMaNhomMon() : null)
                .build();
    }

    @Override
    protected String[] getSearchFields() {
        return new String[]{"maMon", "tenMon"};
    }
}
