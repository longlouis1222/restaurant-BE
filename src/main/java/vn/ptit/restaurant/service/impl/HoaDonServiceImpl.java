package vn.ptit.restaurant.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.ptit.restaurant.common.CodeGenerator;
import vn.ptit.restaurant.dto.request.HoaDonSearchRequest;
import vn.ptit.restaurant.dto.request.TaoHoaDonRequest;
import vn.ptit.restaurant.dto.response.HoaDonResponse;
import vn.ptit.restaurant.dto.response.PageResponse;
import vn.ptit.restaurant.entity.BanAn;
import vn.ptit.restaurant.entity.ChiTietHoaDon;
import vn.ptit.restaurant.entity.HoaDon;
import vn.ptit.restaurant.entity.MonAn;
import vn.ptit.restaurant.entity.id.ChiTietHoaDonId;
import vn.ptit.restaurant.exception.NotFoundException;
import vn.ptit.restaurant.repository.BanAnRepository;
import vn.ptit.restaurant.repository.ChiTietHoaDonRepository;
import vn.ptit.restaurant.repository.HoaDonRepository;
import vn.ptit.restaurant.repository.MonAnRepository;
import vn.ptit.restaurant.service.HoaDonService;

import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class HoaDonServiceImpl implements HoaDonService {

    private final HoaDonRepository hoaDonRepository;
    private final BanAnRepository banAnRepository;
    private final MonAnRepository monAnRepository;
    private final ChiTietHoaDonRepository chiTietHoaDonRepository;

    @Override
    public HoaDonResponse taoHoaDon(TaoHoaDonRequest request) {

        BanAn banAn = banAnRepository.findById(request.getBanId())
                .orElseThrow(() -> new NotFoundException("Không tìm thấy bàn"));

        // 2️⃣ Lấy danh sách id món ăn
        List<String> monAnIds = request.getChiTietList()
                .stream()
                .map(TaoHoaDonRequest.ChiTietRequest::getMonAnId)
                .collect(Collectors.toList());

        // 3️⃣ Query tất cả món ăn
        List<MonAn> monAns = monAnRepository.findAllById(monAnIds);

        // Map để tìm nhanh theo id
        Map<String, MonAn> monAnMap = monAns.stream()
                .collect(Collectors.toMap(MonAn::getMaMon, m -> m));

        // 4️⃣ Tạo hóa đơn
        String newId = CodeGenerator.generateCode("HD", 10);
        HoaDon hoaDon = HoaDon.builder()
                .maHoaDon(newId)
                .trangThai(HoaDon.TrangThaiHoaDon.NEW)
                .ngayLap(LocalDateTime.now())
                .banAn(banAn)
                .tongTien(BigDecimal.ZERO)
                .build();

        hoaDonRepository.save(hoaDon);

        BigDecimal tongTien = BigDecimal.ZERO;

        // 5️⃣ Tạo chi tiết hóa đơn
        for (TaoHoaDonRequest.ChiTietRequest ct : request.getChiTietList()) {

            MonAn monAn = monAnMap.get(ct.getMonAnId());

            if (monAn == null) {
                throw new RuntimeException("Món ăn không tồn tại: " + ct.getMonAnId());
            }

            BigDecimal thanhTien = monAn.getDonGia()
                    .multiply(BigDecimal.valueOf(ct.getSoLuong()));

            ChiTietHoaDonId id = new ChiTietHoaDonId(
                    hoaDon.getMaHoaDon(),
                    monAn.getMaMon()
            );
            ChiTietHoaDon chiTiet = ChiTietHoaDon.builder()
                    .id(id)
                    .hoaDon(hoaDon)
                    .monAn(monAn)
                    .soLuong(ct.getSoLuong())
                    .donGia(monAn.getDonGia())
//                    .thanhTien(thanhTien)
                    .build();

            chiTietHoaDonRepository.save(chiTiet);

            tongTien = tongTien.add(thanhTien);
        }

        // 6️⃣ Update tổng tiền
        hoaDon.setTongTien(tongTien);
        hoaDonRepository.save(hoaDon);

        // update bàn
        banAn.setTrangThai(BanAn.TrangThaiBan.OCCUPIED);

        return mapToResponse(hoaDon);
    }

    @Override
    public void thanhToan(String maHoaDon) {

        HoaDon hoaDon = hoaDonRepository.findById(maHoaDon)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy hóa đơn"));

        hoaDon.setTrangThai(HoaDon.TrangThaiHoaDon.PAID);

        hoaDon.getBanAn().setTrangThai(BanAn.TrangThaiBan.AVAILABLE);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<HoaDonResponse> search(HoaDonSearchRequest request) {

        Specification<HoaDon> specification = (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (request.getId() != null) {
                predicates.add(cb.equal(root.get("maHoaDon"), request.getId()));
            }

            if (request.getBanId() != null) {
                predicates.add(cb.equal(root.get("banAn").get("maBan"), request.getBanId()));
            }

            if (request.getTrangThai() != null) {
                predicates.add(cb.equal(root.get("trangThai"), request.getTrangThai()));
            }

            if (request.getTuNgay() != null && request.getDenNgay() != null) {
                predicates.add(cb.between(
                        root.get("ngayLap"),
                        request.getTuNgay(),
                        request.getDenNgay()
                ));
            }

            if (request.getTongTienTu() != null && request.getTongTienDen() != null) {
                predicates.add(cb.between(
                        root.get("tongTien"),
                        request.getTongTienTu(),
                        request.getTongTienDen()
                ));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Sort sort = request.getSortDir().equalsIgnoreCase("asc") ?
                Sort.by(request.getSortBy()).ascending() :
                Sort.by(request.getSortBy()).descending();

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), sort);

        Page<HoaDon> pageResult = hoaDonRepository.findAll(specification, pageable);

        List<HoaDonResponse> content = pageResult.getContent()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return PageResponse.<HoaDonResponse>builder()
                .content(content)
                .page(pageResult.getNumber())
                .size(pageResult.getSize())
                .totalElements(pageResult.getTotalElements())
                .totalPages(pageResult.getTotalPages())
                .build();
    }

    private HoaDonResponse mapToResponse(HoaDon h) {
        HoaDonResponse response = new HoaDonResponse();
        response.setMaHoaDon(h.getMaHoaDon());
        response.setTrangThai(h.getTrangThai().name());
        response.setTongTien(h.getTongTien());
        return response;
    }
}