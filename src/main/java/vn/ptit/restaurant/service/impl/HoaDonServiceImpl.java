package vn.ptit.restaurant.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import vn.ptit.restaurant.dto.request.HoaDonSearchRequest;
import vn.ptit.restaurant.dto.request.TaoHoaDonRequest;
import vn.ptit.restaurant.dto.response.HoaDonResponse;
import vn.ptit.restaurant.dto.response.PageResponse;
import vn.ptit.restaurant.entity.*;
import vn.ptit.restaurant.exception.NotFoundException;
import vn.ptit.restaurant.repository.*;
import vn.ptit.restaurant.service.HoaDonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class HoaDonServiceImpl implements HoaDonService {

    private final HoaDonRepository hoaDonRepository;
    private final BanAnRepository banAnRepository;
    private final MonAnRepository monAnRepository;

    @Override
    public HoaDonResponse taoHoaDon(TaoHoaDonRequest request) {

        BanAn banAn = banAnRepository.findById(request.getBanId())
                .orElseThrow(() -> new NotFoundException("Không tìm thấy bàn"));

        List<MonAn> monAns = monAnRepository.findAllById(request.getChiTietList());


        if (monAn.isEmpty()) {
            throw new NotFoundException("Danh sách món không hợp lệ");
        }

        BigDecimal tongTien = monAn.stream()
                .map(MonAn::getDonGia)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        HoaDon hoaDon = new HoaDon();
        hoaDon.setMaHoaDon("HD-" + System.currentTimeMillis());
        hoaDon.setTrangThai(HoaDon.TrangThaiHoaDon.NEW);
        hoaDon.setNgayLap(LocalDateTime.now());
        hoaDon.setTongTien(tongTien);
        hoaDon.setBanAn(banAn);

        hoaDonRepository.save(hoaDon);

        // 🔥 AUTO UPDATE TRẠNG THÁI BÀN
        banAn.setTrangThai(banAn.TrangThaiBan.OCCUPIED);

        return HoaDonResponse.builder()
                .id(hoaDon.getId())
                .maHoaDon(hoaDon.getMaHoaDon())
                .tongTien(tongTien)
                .trangThai(hoaDon.getTrangThai().name())
                .build();
    }

    @Override
    public void thanhToan(Long hoaDonId) {

        HoaDon hoaDon = hoaDonRepository.findById(hoaDonId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy hóa đơn"));

        hoaDon.setTrangThai(HoaDon.TrangThaiHoaDon.PAID);

        // 🔥 TỰ ĐỘNG TRẢ BÀN
        hoaDon.getBanAn().setTrangThai(banAnRepository.TrangThaiBan.AVAILABLE);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<HoaDonResponse> search(HoaDonSearchRequest request) {

        Specification<HoaDon> specification = (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (request.getId() != null) {
                predicates.add(cb.equal(root.get("id"), request.getId()));
            }

            if (request.getBanId() != null) {
                predicates.add(cb.equal(root.get("banAn").get("id"), request.getBanId()));
            }

            if (request.getTrangThai() != null) {
                predicates.add(cb.equal(root.get("trangThai"), request.getTrangThai()));
            }

            if (request.getTuNgay() != null && request.getDenNgay() != null) {
                predicates.add(cb.between(
                        root.get("thoiGianTao"),
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

        List<HoaDonResponse> content = pageResult.getContent().stream()
                .map(h -> HoaDonResponse.builder()
                        .id(h.getMaHoaDon())
                        .banId(h.getBan().getId())
                        .tongTien(h.getTongTien())
                        .thoiGianTao(h.getThoiGianTao())
                        .build())
                .toList();

        return PageResponse.<HoaDonResponse>builder()
                .content(content)
                .page(pageResult.getNumber())
                .size(pageResult.getSize())
                .totalElements(pageResult.getTotalElements())
                .totalPages(pageResult.getTotalPages())
                .build();
    }
}