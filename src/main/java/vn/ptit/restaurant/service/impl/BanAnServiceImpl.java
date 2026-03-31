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
import vn.ptit.restaurant.entity.HoaDon;
import vn.ptit.restaurant.repository.BanAnRepository;
import vn.ptit.restaurant.repository.HoaDonRepository;
import vn.ptit.restaurant.service.BanAnService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BanAnServiceImpl extends AbstractCatalogService<
        BanAn, String, BanAnRequest, BanAnRequest, BanAnResponse, BanAnSearchRequest>
        implements BanAnService {

    private final BanAnRepository repository;
    private final HoaDonRepository hoaDonRepository;

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
        BanAnResponse.BanAnResponseBuilder builder = BanAnResponse.builder()
                .maBan(entity.getMaBan())
                .soCho(entity.getSoCho())
                .khuVuc(entity.getKhuVuc())
                .tanSuatSuDung(entity.getTanSuatSuDung())
                .trangThai(entity.getTrangThai());

        // Lấy hóa đơn NEW hoặc PAID gần nhất của bàn để hiển thị thông tin khách + tổng tiền
        List<HoaDon> hoaDons = hoaDonRepository.findByBanAn_MaBanAndTrangThaiInOrderByNgayLapDesc(
                entity.getMaBan(),
                java.util.Arrays.asList(HoaDon.TrangThaiHoaDon.NEW, HoaDon.TrangThaiHoaDon.PAID)
        );

        if (!hoaDons.isEmpty()) {
            HoaDon hoaDon = hoaDons.get(0);
            builder.maHoaDon(hoaDon.getMaHoaDon());
            builder.tongTienHoaDon(hoaDon.getTongTien());
            if (hoaDon.getKhachHang() != null) {
                builder.tenKhach(hoaDon.getKhachHang().getTenKhachHang());
            }

            // Khi bàn có hóa đơn NEW thì tương đương với bàn đó bị chiếm (OCCUPIED)
            if (hoaDon.getTrangThai() == HoaDon.TrangThaiHoaDon.NEW) {
                builder.trangThai(BanAn.TrangThaiBan.OCCUPIED);
            }
        }

        return builder.build();
    }

    @Override
    protected String[] getSearchFields() {
        return new String[]{"maBan"};
    }

    @Override
    public List<BanAnResponse> getAllWithCurrentBill() {
        List<BanAn> banAns = repository.findAll();
        if (banAns.isEmpty()) {
            return Collections.emptyList();
        }

        List<String> maBans = banAns.stream()
                .map(BanAn::getMaBan)
                .collect(Collectors.toList());

        List<HoaDon> hoaDons = hoaDonRepository
                .findByBanAn_MaBanInAndTrangThaiInOrderByBanAn_MaBanAscNgayLapDesc(
                        maBans,
                        Arrays.asList(HoaDon.TrangThaiHoaDon.NEW, HoaDon.TrangThaiHoaDon.PAID)
                );

        // Lấy hóa đơn mới nhất cho mỗi bàn
        Map<String, HoaDon> latestBillByTable = new java.util.HashMap<>();
        for (HoaDon hd : hoaDons) {
            String maBan = hd.getBanAn().getMaBan();
            // do query đã sort theo maBan asc, ngayLap desc nên gặp bàn lần đầu là hóa đơn mới nhất
            latestBillByTable.putIfAbsent(maBan, hd);
        }

        return banAns.stream()
                .map(ban -> {
                    HoaDon currentBill = latestBillByTable.get(ban.getMaBan());
                    return mapToResponseWithBill(ban, currentBill);
                })
                .collect(Collectors.toList());
    }

    private BanAnResponse mapToResponseWithBill(BanAn entity, HoaDon hoaDon) {
        BanAnResponse.BanAnResponseBuilder builder = BanAnResponse.builder()
                .maBan(entity.getMaBan())
                .soCho(entity.getSoCho())
                .khuVuc(entity.getKhuVuc())
                .tanSuatSuDung(entity.getTanSuatSuDung())
                .trangThai(entity.getTrangThai());

        if (hoaDon != null && hoaDon.getTrangThai() == HoaDon.TrangThaiHoaDon.NEW) {
            // Chỉ set thông tin hóa đơn nếu trạng thái là NEW
            builder.maHoaDon(hoaDon.getMaHoaDon());
            builder.tongTienHoaDon(hoaDon.getTongTien());
            if (hoaDon.getKhachHang() != null) {
                builder.tenKhach(hoaDon.getKhachHang().getTenKhachHang());
            }
            // Khi bàn có hóa đơn NEW thì tương đương với bàn đó bị chiếm (OCCUPIED)
            builder.trangThai(BanAn.TrangThaiBan.OCCUPIED);
        }

        return builder.build();
    }
}
