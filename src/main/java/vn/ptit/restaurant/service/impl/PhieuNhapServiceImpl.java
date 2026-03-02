package vn.ptit.restaurant.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.ptit.restaurant.common.catalog.AbstractCatalogService;
import vn.ptit.restaurant.common.catalog.CatalogServiceHelper;
import vn.ptit.restaurant.dto.request.PhieuNhapRequest;
import vn.ptit.restaurant.dto.request.PhieuNhapSearchRequest;
import vn.ptit.restaurant.dto.response.PageResponse;
import vn.ptit.restaurant.dto.response.PhieuNhapResponse;
import vn.ptit.restaurant.entity.PhieuNhap;
import vn.ptit.restaurant.repository.PhieuNhapRepository;
import vn.ptit.restaurant.entity.ChiTietPhieuNhap;
import vn.ptit.restaurant.entity.id.ChiTietPhieuNhapId;
import vn.ptit.restaurant.repository.NguyenLieuRepository;
import vn.ptit.restaurant.repository.NhaCungCapRepository;
import vn.ptit.restaurant.repository.NhanVienRepository;
import vn.ptit.restaurant.repository.ChiTietPhieuNhapRepository;
import vn.ptit.restaurant.service.PhieuNhapService;
import vn.ptit.restaurant.exception.NotFoundException;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PhieuNhapServiceImpl extends AbstractCatalogService<
        PhieuNhap, String, PhieuNhapRequest, PhieuNhapRequest, PhieuNhapResponse, PhieuNhapSearchRequest>
        implements PhieuNhapService {

    private final PhieuNhapRepository repository;
    private final NhaCungCapRepository nhaCungCapRepository;
    private final NhanVienRepository nhanVienRepository;
    private final NguyenLieuRepository nguyenLieuRepository;
    private final ChiTietPhieuNhapRepository chiTietPhieuNhapRepository;

    @Override
    protected JpaRepository<PhieuNhap, String> getRepository() {
        return repository;
    }

    @Override
    protected JpaSpecificationExecutor<PhieuNhap> getSpecRepository() {
        return repository;
    }

    @Override
    protected String generateIdIfNeeded(PhieuNhapRequest request) {
        if (request.getMaPhieuNhap() == null || request.getMaPhieuNhap().trim().isEmpty()) {
            return "PN-" + System.currentTimeMillis();
        }
        return request.getMaPhieuNhap();
    }

    @Override
    protected PhieuNhap createEntity(PhieuNhapRequest request, String id) {
        PhieuNhap p = PhieuNhap.builder()
                .maPhieuNhap(id)
                .nhaCungCap(nhaCungCapRepository.findById(request.getMaNcc()).orElse(null))
                .nhanVien(nhanVienRepository.findById(request.getMaNhanVien()).orElse(null))
                .ngayNhap(request.getNgayNhap())
                .build();

        // save phieu to get persisted
        PhieuNhap saved = repository.save(p);

        // save chi tiet
        if (request.getChiTietList() != null) {
            List<ChiTietPhieuNhap> cts = request.getChiTietList().stream().map(ct -> {
                ChiTietPhieuNhapId idct = new ChiTietPhieuNhapId(saved.getMaPhieuNhap(), ct.getMaNguyenLieu());
                return ChiTietPhieuNhap.builder()
                        .id(idct)
                        .phieuNhap(saved)
                        .nguyenLieu(nguyenLieuRepository.findById(ct.getMaNguyenLieu()).orElse(null))
                        .soLuong(ct.getSoLuong())
                        .donGia(ct.getDonGia())
                        .build();
            }).collect(Collectors.toList());
            chiTietPhieuNhapRepository.saveAll(cts);
        }

        return saved;
    }

    @Override
    protected void applyUpdate(PhieuNhap entity, PhieuNhapRequest request) {
        if (request.getMaNcc() != null) entity.setNhaCungCap(nhaCungCapRepository.findById(request.getMaNcc()).orElse(null));
        if (request.getMaNhanVien() != null) entity.setNhanVien(nhanVienRepository.findById(request.getMaNhanVien()).orElse(null));
        if (request.getNgayNhap() != null) entity.setNgayNhap(request.getNgayNhap());
        if (request.getChiTietList() != null) {
            chiTietPhieuNhapRepository.deleteByPhieuNhapMaPhieuNhap(entity.getMaPhieuNhap());
            chiTietPhieuNhapRepository.flush();
            List<ChiTietPhieuNhap> cts = request.getChiTietList().stream().map(ct -> {
                ChiTietPhieuNhapId idct = new ChiTietPhieuNhapId(entity.getMaPhieuNhap(), ct.getMaNguyenLieu());
                return ChiTietPhieuNhap.builder()
                        .id(idct)
                        .phieuNhap(entity)
                        .nguyenLieu(nguyenLieuRepository.findById(ct.getMaNguyenLieu())
                                .orElseThrow(() -> new NotFoundException("Không tìm thấy nguyên liệu: " + ct.getMaNguyenLieu())))
                        .soLuong(ct.getSoLuong())
                        .donGia(ct.getDonGia())
                        .build();
            }).collect(Collectors.toList());
            chiTietPhieuNhapRepository.saveAll(cts);
        }
    }

    @Override
    protected PhieuNhapResponse toResponse(PhieuNhap entity) {
        PhieuNhapResponse resp = PhieuNhapResponse.builder()
                .maPhieuNhap(entity.getMaPhieuNhap())
                .maNcc(entity.getNhaCungCap() != null ? entity.getNhaCungCap().getMaNcc() : null)
                .maNhanVien(entity.getNhanVien() != null ? entity.getNhanVien().getMaNhanVien() : null)
                .ngayNhap(entity.getNgayNhap())
                .build();

        // load chi tiet via repository
        List<ChiTietPhieuNhap> chiTiets = chiTietPhieuNhapRepository.findByPhieuNhapMaPhieuNhap(entity.getMaPhieuNhap());
        List<PhieuNhapResponse.ChiTietResponse> ct = chiTiets != null ? chiTiets.stream().map(c ->
                PhieuNhapResponse.ChiTietResponse.builder()
                        .maNguyenLieu(c.getId().getMaNguyenLieu())
                        .soLuong(c.getSoLuong())
                        .donGia(c.getDonGia())
                        .build()
        ).collect(Collectors.toList()) : null;

        resp.setChiTietList(ct);
        return resp;
    }

    @Override
    protected String[] getSearchFields() {
        return new String[]{"maPhieuNhap"};
    }

    @Override
    public PageResponse<PhieuNhapResponse> search(PhieuNhapSearchRequest request) {
        Specification<PhieuNhap> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (request.getQ() != null && !request.getQ().trim().isEmpty()) {
                String like = "%" + request.getQ().trim().toLowerCase() + "%";
                predicates.add(cb.like(cb.lower(root.get("maPhieuNhap").as(String.class)), like));
            }
            if (request.getMaNcc() != null && !request.getMaNcc().trim().isEmpty()) {
                predicates.add(cb.equal(root.get("nhaCungCap").get("maNcc"), request.getMaNcc().trim()));
            }
            if (request.getMaNhanVien() != null && !request.getMaNhanVien().trim().isEmpty()) {
                predicates.add(cb.equal(root.get("nhanVien").get("maNhanVien"), request.getMaNhanVien().trim()));
            }
            return predicates.isEmpty() ? cb.conjunction() : cb.and(predicates.toArray(new Predicate[0]));
        };
        Pageable pageable = CatalogServiceHelper.toPageable(request);
        Page<PhieuNhap> page = repository.findAll(spec, pageable);
        return CatalogServiceHelper.toPageResponse(page, this::toResponse);
    }
}
