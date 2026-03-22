package vn.ptit.restaurant.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.ptit.restaurant.common.catalog.AbstractCatalogService;
import vn.ptit.restaurant.common.CodeGenerator;
import vn.ptit.restaurant.dto.request.NguyenLieuRequest;
import vn.ptit.restaurant.dto.request.NguyenLieuSearchRequest;
import vn.ptit.restaurant.dto.response.NguyenLieuResponse;
import vn.ptit.restaurant.entity.NguyenLieu;
import vn.ptit.restaurant.repository.NguyenLieuRepository;
import vn.ptit.restaurant.service.NguyenLieuService;

@Service
@RequiredArgsConstructor
@Transactional
public class NguyenLieuServiceImpl extends AbstractCatalogService<
        NguyenLieu, String, NguyenLieuRequest, NguyenLieuRequest, NguyenLieuResponse, NguyenLieuSearchRequest>
        implements NguyenLieuService {

    private final NguyenLieuRepository repository;

    @Override
    protected JpaRepository<NguyenLieu, String> getRepository() {
        return repository;
    }

    @Override
    protected JpaSpecificationExecutor<NguyenLieu> getSpecRepository() {
        return repository;
    }

    @Override
    protected String generateIdIfNeeded(NguyenLieuRequest request) {
        if (request.getMaNguyenLieu() == null || request.getMaNguyenLieu().trim().isEmpty()) {
            return CodeGenerator.generateCode("NL", 10);
        }
        return request.getMaNguyenLieu();
    }

    @Override
    protected NguyenLieu createEntity(NguyenLieuRequest request, String id) {
        return NguyenLieu.builder()
                .maNguyenLieu(id)
                .tenNguyenLieu(request.getTenNguyenLieu())
                .donViTinh(request.getDonViTinh())
                .giaNhap(request.getGiaNhap())
                .build();
    }

    @Override
    protected void applyUpdate(NguyenLieu entity, NguyenLieuRequest request) {
        if (request.getTenNguyenLieu() != null) entity.setTenNguyenLieu(request.getTenNguyenLieu());
        if (request.getDonViTinh() != null) entity.setDonViTinh(request.getDonViTinh());
        if (request.getGiaNhap() != null) entity.setGiaNhap(request.getGiaNhap());
    }

    @Override
    protected NguyenLieuResponse toResponse(NguyenLieu entity) {
        return NguyenLieuResponse.builder()
                .maNguyenLieu(entity.getMaNguyenLieu())
                .tenNguyenLieu(entity.getTenNguyenLieu())
                .donViTinh(entity.getDonViTinh())
                .giaNhap(entity.getGiaNhap())
                .build();
    }

    @Override
    protected String[] getSearchFields() {
        return new String[]{"maNguyenLieu", "tenNguyenLieu"};
    }
}
