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
import vn.ptit.restaurant.entity.KhachHang;
import vn.ptit.restaurant.entity.MonAn;
import vn.ptit.restaurant.entity.id.ChiTietHoaDonId;
import vn.ptit.restaurant.exception.NotFoundException;
import vn.ptit.restaurant.repository.BanAnRepository;
import vn.ptit.restaurant.repository.ChiTietHoaDonRepository;
import vn.ptit.restaurant.repository.HoaDonRepository;
import vn.ptit.restaurant.repository.KhachHangRepository;
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
    private final KhachHangRepository khachHangRepository;

    @Override
    public HoaDonResponse taoHoaDon(TaoHoaDonRequest request) {

        BanAn banAn = banAnRepository.findById(request.getBanId())
                .orElseThrow(() -> new NotFoundException("Không tìm thấy bàn"));

        // Xử lý KhachHang: tìm hoặc tạo mới theo SĐT
        KhachHang khachHang = null;
        if (request.getSoDienThoai() != null && !request.getSoDienThoai().trim().isEmpty()) {
            // Tìm khách hàng theo SĐT
            khachHang = khachHangRepository.findBySdt(request.getSoDienThoai()).orElse(null);

            // Nếu không tìm thấy, tạo mới
            if (khachHang == null) {
                String newKhachHangId = CodeGenerator.generateCode("KH", 10);
                KhachHang newKh = KhachHang.builder()
                        .maKhachHang(newKhachHangId)
                        .tenKhachHang(request.getTenKhachHang() != null ? request.getTenKhachHang() : "Khách")
                        .sdt(request.getSoDienThoai())
                        .diemTichLuy(BigDecimal.ZERO)
                        .build();
                // Gán lại khachHang bằng entity đã được save (managed)
                khachHang = khachHangRepository.save(newKh);
            }
        }

        // 2 Lấy danh sách id món ăn
        List<String> monAnIds = request.getChiTietList()
                .stream()
                .map(TaoHoaDonRequest.ChiTietRequest::getMonAnId)
                .collect(Collectors.toList());

        // 3 Query tất cả món ăn
        List<MonAn> monAns = monAnRepository.findAllById(monAnIds);

        // Map để tìm nhanh theo id
        Map<String, MonAn> monAnMap = monAns.stream()
                .collect(Collectors.toMap(MonAn::getMaMon, m -> m));

        // 4 Tạo hóa đơn
        String newId = CodeGenerator.generateCode("HD", 10);
        HoaDon hoaDon = HoaDon.builder()
                .maHoaDon(newId)
                .khachHang(khachHang)
                .trangThai(HoaDon.TrangThaiHoaDon.NEW)
                .ngayLap(LocalDateTime.now())
                .banAn(banAn)
                .tongTien(BigDecimal.ZERO)
                .build();

        hoaDonRepository.save(hoaDon);

        BigDecimal tongTien = BigDecimal.ZERO;

        // 5 Tạo chi tiết hóa đơn
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

        // 6 Update tổng tiền
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

        // Khi hóa đơn được thanh toán, bàn sẽ được trả về trạng thái AVAILABLE
        // => Đảm bảo luồng trạng thái bàn được reset sau khi thanh toán xong
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

        List<HoaDon> hoaDons = pageResult.getContent();

        List<String> maHoaDonList = hoaDons.stream()
                .map(HoaDon::getMaHoaDon)
                .collect(Collectors.toList());

        Map<String, List<ChiTietHoaDon>> chiTietByHoaDon;
        if (maHoaDonList.isEmpty()) {
            chiTietByHoaDon = java.util.Collections.emptyMap();
        } else {
            List<ChiTietHoaDon> allChiTiet = chiTietHoaDonRepository.findByHoaDon_MaHoaDonIn(maHoaDonList);
            chiTietByHoaDon = allChiTiet.stream()
                    .collect(Collectors.groupingBy(ct -> ct.getHoaDon().getMaHoaDon()));
        }

        List<HoaDonResponse> content = hoaDons.stream()
                .map(hd -> mapToResponse(hd, chiTietByHoaDon.get(hd.getMaHoaDon())))
                .collect(Collectors.toList());

        return PageResponse.<HoaDonResponse>builder()
                .content(content)
                .page(pageResult.getNumber())
                .size(pageResult.getSize())
                .totalElements(pageResult.getTotalElements())
                .totalPages(pageResult.getTotalPages())
                .build();
    }

    @Override
    public HoaDonResponse capNhatHoaDon(String maHoaDon, TaoHoaDonRequest request) {

        HoaDon hoaDon = hoaDonRepository.findById(maHoaDon)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy hóa đơn"));

        // Cập nhật bàn nếu có thay đổi banId
        if (request.getBanId() != null && !request.getBanId().equals(hoaDon.getBanAn().getMaBan())) {
            BanAn banAnMoi = banAnRepository.findById(request.getBanId())
                    .orElseThrow(() -> new NotFoundException("Không tìm thấy bàn"));
            hoaDon.setBanAn(banAnMoi);
        }

        // Xử lý khách hàng: tìm hoặc tạo mới theo SĐT (giống logic tạo hóa đơn)
        KhachHang khachHang = hoaDon.getKhachHang();
        if (request.getSoDienThoai() != null && !request.getSoDienThoai().trim().isEmpty()) {
            khachHang = khachHangRepository.findBySdt(request.getSoDienThoai()).orElse(null);
            if (khachHang == null) {
                String newKhachHangId = CodeGenerator.generateCode("KH", 10);
                KhachHang newKh = KhachHang.builder()
                        .maKhachHang(newKhachHangId)
                        .tenKhachHang(request.getTenKhachHang() != null ? request.getTenKhachHang() : "Khách")
                        .sdt(request.getSoDienThoai())
                        .diemTichLuy(BigDecimal.ZERO)
                        .build();
                // Gán lại khachHang bằng entity đã được save (managed)
                khachHang = khachHangRepository.save(newKh);
            }
        }
        hoaDon.setKhachHang(khachHang);

        // Xóa toàn bộ chi tiết cũ
        chiTietHoaDonRepository.deleteAllByHoaDon_MaHoaDon(maHoaDon);

        // Nếu không có chi tiết mới, đặt tổng tiền = 0 và trả về
        if (request.getChiTietList() == null || request.getChiTietList().isEmpty()) {
            hoaDon.setTongTien(BigDecimal.ZERO);
            hoaDonRepository.save(hoaDon);
            return mapToResponse(hoaDon);
        }

        // Lấy danh sách id món ăn mới
        List<String> monAnIds = request.getChiTietList().stream()
                .map(TaoHoaDonRequest.ChiTietRequest::getMonAnId)
                .collect(Collectors.toList());

        List<MonAn> monAns = monAnRepository.findAllById(monAnIds);
        Map<String, MonAn> monAnMap = monAns.stream()
                .collect(Collectors.toMap(MonAn::getMaMon, m -> m));

        BigDecimal tongTien = BigDecimal.ZERO;

        // Tạo lại chi tiết hóa đơn
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
                    .build();

            chiTietHoaDonRepository.save(chiTiet);

            tongTien = tongTien.add(thanhTien);
        }

        hoaDon.setTongTien(tongTien);
        hoaDonRepository.save(hoaDon);

        return mapToResponse(hoaDon);
    }

    private HoaDonResponse mapToResponse(HoaDon h) {
        // Dùng cho các chỗ gọi lẻ (ví dụ tạo mới xong trả về 1 hóa đơn)
        List<ChiTietHoaDon> chiTietEntities = chiTietHoaDonRepository.findByHoaDon_MaHoaDon(h.getMaHoaDon());
        return mapToResponse(h, chiTietEntities);
    }

    private HoaDonResponse mapToResponse(HoaDon h, List<ChiTietHoaDon> chiTietEntities) {
        HoaDonResponse response = new HoaDonResponse();
        response.setMaHoaDon(h.getMaHoaDon());
        response.setTrangThai(h.getTrangThai().name());
        response.setTongTien(h.getTongTien());
        response.setNgayLap(h.getNgayLap());

        // Thêm thông tin khách hàng
        if (h.getKhachHang() != null) {
            response.setTenKhachHang(h.getKhachHang().getTenKhachHang());
            response.setSoDienThoai(h.getKhachHang().getSdt());
        }

        List<HoaDonResponse.ChiTietResponse> chiTietList =
                (chiTietEntities == null ? java.util.Collections.<ChiTietHoaDon>emptyList() : chiTietEntities)
                        .stream()
                        .map(ct -> {
                            BigDecimal thanhTien = (ct.getDonGia() != null && ct.getSoLuong() != null)
                                    ? ct.getDonGia().multiply(BigDecimal.valueOf(ct.getSoLuong()))
                                    : BigDecimal.ZERO;

                            return HoaDonResponse.ChiTietResponse.builder()
                                    .maMon(ct.getMonAn() != null ? ct.getMonAn().getMaMon() : null)
                                    .tenMon(ct.getMonAn() != null ? ct.getMonAn().getTenMon() : null)
                                    .soLuong(ct.getSoLuong())
                                    .donGia(ct.getDonGia())
                                    .thanhTien(thanhTien)
                                    .build();
                        })
                        .collect(Collectors.toList());

        response.setChiTietList(chiTietList);

        return response;
    }
}
