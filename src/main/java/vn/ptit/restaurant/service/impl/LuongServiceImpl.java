package vn.ptit.restaurant.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.ptit.restaurant.common.catalog.AbstractCatalogService;
import vn.ptit.restaurant.common.CodeGenerator;
import vn.ptit.restaurant.dto.request.LuongRequest;
import vn.ptit.restaurant.dto.request.LuongSearchRequest;
import vn.ptit.restaurant.dto.response.LuongResponse;
import vn.ptit.restaurant.entity.Luong;
import vn.ptit.restaurant.repository.LuongRepository;
import vn.ptit.restaurant.service.LuongService;

@Service
@RequiredArgsConstructor
@Transactional
public class LuongServiceImpl extends AbstractCatalogService<
        Luong, String, LuongRequest, LuongRequest, LuongResponse, LuongSearchRequest>
        implements LuongService {

    private final LuongRepository repository;

    @Override
    protected JpaRepository<Luong, String> getRepository() {
        return repository;
    }

    @Override
    protected JpaSpecificationExecutor<Luong> getSpecRepository() {
        return repository;
    }

    @Override
    protected String generateIdIfNeeded(LuongRequest request) {
        if (request.getMaLuong() == null || request.getMaLuong().trim().isEmpty()) {
            return CodeGenerator.generateCode("L", 10);
        }
        return request.getMaLuong();
    }

    @Override
    protected Luong createEntity(LuongRequest request, String id) {
        return Luong.builder()
                .maLuong(id)
                .mucLuong(request.getMucLuong())
                .ngayCongTheoThang(request.getNgayCongTheoThang())
                .ngayCongThucTe(request.getNgayCongThucTe())
                .thuong(request.getThuong())
                .phat(request.getPhat())
                .ghiChu(request.getGhiChu())
                .build();
    }

    @Override
    protected void applyUpdate(Luong entity, LuongRequest request) {
        if (request.getMucLuong() != null) entity.setMucLuong(request.getMucLuong());
        if (request.getNgayCongTheoThang() != null) entity.setNgayCongTheoThang(request.getNgayCongTheoThang());
        if (request.getNgayCongThucTe() != null) entity.setNgayCongThucTe(request.getNgayCongThucTe());
        if (request.getThuong() != null) entity.setThuong(request.getThuong());
        if (request.getPhat() != null) entity.setPhat(request.getPhat());
        if (request.getGhiChu() != null) entity.setGhiChu(request.getGhiChu());
    }

    @Override
    protected LuongResponse toResponse(Luong entity) {
        return LuongResponse.builder()
                .maLuong(entity.getMaLuong())
                .mucLuong(entity.getMucLuong())
                .ngayCongTheoThang(entity.getNgayCongTheoThang())
                .ngayCongThucTe(entity.getNgayCongThucTe())
                .thuong(entity.getThuong())
                .phat(entity.getPhat())
                .ghiChu(entity.getGhiChu())
                .build();
    }

    @Override
    protected String[] getSearchFields() {
        return new String[]{"maLuong"};
    }
}
