package vn.ptit.restaurant.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.ptit.restaurant.common.CodeGenerator;
import vn.ptit.restaurant.common.catalog.AbstractCatalogService;
import vn.ptit.restaurant.dto.request.ChucVuRequest;
import vn.ptit.restaurant.dto.request.ChucVuSearchRequest;
import vn.ptit.restaurant.dto.response.ChucVuResponse;
import vn.ptit.restaurant.entity.ChucVu;
import vn.ptit.restaurant.entity.Luong;
import vn.ptit.restaurant.repository.ChucVuRepository;
import vn.ptit.restaurant.service.ChucVuService;

@Service
@RequiredArgsConstructor
@Transactional
public class ChucVuServiceImpl extends AbstractCatalogService<
        ChucVu, String, ChucVuRequest, ChucVuRequest, ChucVuResponse, ChucVuSearchRequest>
        implements ChucVuService {

    private final ChucVuRepository repository;

    @Override
    protected JpaRepository<ChucVu, String> getRepository() {
        return repository;
    }

    @Override
    protected JpaSpecificationExecutor<ChucVu> getSpecRepository() {
        return repository;
    }

    @Override
    protected String generateIdIfNeeded(ChucVuRequest request) {
        if (request.getMaChucVu() == null || request.getMaChucVu().trim().isEmpty()) {
            // Generate a code with prefix "CV" and total length 10 to fit varchar(10)
            return CodeGenerator.generateCode("CV", 10);
        }
        return request.getMaChucVu();
    }

    @Override
    protected ChucVu createEntity(ChucVuRequest request, String id) {
        Luong luong = new Luong(); // Assuming Luong is a valid entity
        luong.setMaLuong(request.getMaLuong()); // Set the maLuong field in Luong

        return ChucVu.builder()
                .maChucVu(id)
                .tenChucVu(request.getTenChucVu())
                .luong(luong) // Set the Luong object
                .build();
    }

    @Override
    protected void applyUpdate(ChucVu entity, ChucVuRequest request) {
        if (request.getTenChucVu() != null) {
            entity.setTenChucVu(request.getTenChucVu());
        }
        if (request.getMaLuong() != null) {
            Luong luong = new Luong();
            luong.setMaLuong(request.getMaLuong());
            entity.setLuong(luong); // Update the Luong object
        }
    }

    @Override
    protected ChucVuResponse toResponse(ChucVu entity) {
        return ChucVuResponse.builder()
                .maChucVu(entity.getMaChucVu())
                .tenChucVu(entity.getTenChucVu())
                .maLuong(entity.getLuong() != null ? entity.getLuong().getMaLuong() : null) // Retrieve maLuong from Luong
                .build();
    }

    @Override
    protected String[] getSearchFields() {
        return new String[]{"maChucVu", "tenChucVu"};
    }
}
