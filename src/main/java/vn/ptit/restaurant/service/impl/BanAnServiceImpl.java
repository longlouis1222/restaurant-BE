package vn.ptit.restaurant.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.ptit.restaurant.common.CodeGenerator;
import vn.ptit.restaurant.common.catalog.AbstractCatalogService;
import vn.ptit.restaurant.dto.request.BanAnRequest;
import vn.ptit.restaurant.dto.request.BanAnSearchRequest;
import vn.ptit.restaurant.dto.response.BanAnResponse;
import vn.ptit.restaurant.entity.BanAn;
import vn.ptit.restaurant.repository.BanAnRepository;
import vn.ptit.restaurant.service.BanAnService;

@Service
@RequiredArgsConstructor
@Transactional
public class BanAnServiceImpl extends AbstractCatalogService<
        BanAn, String, BanAnRequest, BanAnRequest, BanAnResponse, BanAnSearchRequest>
        implements BanAnService {

    private final BanAnRepository repository;

    @Override
    protected JpaRepository<BanAn, String> getRepository() {
        return repository;
    }

    @Override
    protected JpaSpecificationExecutor<BanAn> getSpecRepository() {
        return repository;
    }

    @Override
    protected String generateIdIfNeeded(BanAnRequest request) {
        if (request.getMaBan() == null || request.getMaBan().trim().isEmpty()) {
            return CodeGenerator.generateCode("BA", 10);
        }
        return request.getMaBan();
    }

    @Override
    protected BanAn createEntity(BanAnRequest request, String id) {
        return BanAn.builder()
                .maBan(id)
                .soCho(request.getSoCho())
                .khuVuc(request.getKhuVuc())
                .tanSuatSuDung(request.getTanSuatSuDung())
                .trangThai(request.getTrangThai())
                .build();
    }

    @Override
    protected void applyUpdate(BanAn entity, BanAnRequest request) {
        if (request.getSoCho() != null) entity.setSoCho(request.getSoCho());
        if (request.getKhuVuc() != null) entity.setKhuVuc(request.getKhuVuc());
        if (request.getTanSuatSuDung() != null) entity.setTanSuatSuDung(request.getTanSuatSuDung());
        if (request.getTrangThai() != null) entity.setTrangThai(request.getTrangThai());
    }

    @Override
    protected BanAnResponse toResponse(BanAn entity) {
        return BanAnResponse.builder()
                .maBan(entity.getMaBan())
                .soCho(entity.getSoCho())
                .khuVuc(entity.getKhuVuc())
                .tanSuatSuDung(entity.getTanSuatSuDung())
                .trangThai(entity.getTrangThai())
                .build();
    }

    @Override
    protected String[] getSearchFields() {
        return new String[]{"maBan"};
    }
}
