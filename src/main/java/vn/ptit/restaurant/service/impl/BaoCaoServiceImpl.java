package vn.ptit.restaurant.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import vn.ptit.restaurant.dto.response.*;
import vn.ptit.restaurant.entity.ChucVu;
import vn.ptit.restaurant.entity.NhanVien;
import vn.ptit.restaurant.entity.Luong;
import vn.ptit.restaurant.repository.*;
import vn.ptit.restaurant.service.BaoCaoService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BaoCaoServiceImpl implements BaoCaoService {

    private final HoaDonRepository hoaDonRepository;
    private final ChiTietHoaDonRepository chiTietHoaDonRepository;
    private final ChiTietPhieuNhapRepository chiTietPhieuNhapRepository;
    private final NhanVienRepository nhanVienRepository;
    private final ChucVuRepository chucVuRepository;

    @Override
    public DoanhThuResponse tinhTongDoanhThu(LocalDateTime tuNgay, LocalDateTime denNgay) {
        BigDecimal tong = hoaDonRepository.tinhTongDoanhThu(tuNgay, denNgay);
        return new DoanhThuResponse(tong);
    }

    @Override
    public List<DoanhThuTheoNgayResponse> doanhThuTheoNgay(LocalDate fromDate, LocalDate toDate) {
        LocalDateTime fromDateTime = fromDate.atStartOfDay();
        LocalDateTime toDateTime = toDate.atTime(23, 59, 59);
        return hoaDonRepository.doanhThuTheoNgay(fromDateTime, toDateTime);
    }

    @Override
    public List<DoanhThuTheoThangResponse> doanhThuTheoThang(int nam) {
        return hoaDonRepository.doanhThuTheoThang(nam);
    }

    @Override
    public List<BanAnThongKeResponse> thongKeBanAnTrungBinhTheoNgayTrongThang(int nam, int thang) {
        YearMonth yearMonth = YearMonth.of(nam, thang);
        LocalDate firstDay = yearMonth.atDay(1);
        LocalDate lastDay = yearMonth.atEndOfMonth();
        int soNgayTrongThang = yearMonth.lengthOfMonth();

        LocalDateTime tuNgay = firstDay.atStartOfDay();
        LocalDateTime denNgay = lastDay.atTime(23, 59, 59);

        // Lấy số lần từng loại bàn (theo số chỗ) được sử dụng trong tháng
        List<Object[]> raw = hoaDonRepository.demSoLanSuDungBanTheoSoCho(tuNgay, denNgay);

        List<BanAnThongKeResponse> result = new ArrayList<>();
        for (Object[] row : raw) {
            Integer soCho = (Integer) row[0];
            Long soBanSuDung = (Long) row[1];

            // Trung bình mỗi ngày = tổng số bàn loại đó được sử dụng trong tháng / số ngày
            double trungBinhMoiNgay = soNgayTrongThang > 0
                    ? (double) soBanSuDung / soNgayTrongThang
                    : 0.0;

            result.add(new BanAnThongKeResponse(soCho, trungBinhMoiNgay));
        }

        return result;
    }

    @Override
    public List<MonAnStatisticsResponse> thongKeMonAnTheoThang(int nam, int thang) {
        return chiTietHoaDonRepository.thongKeMonAnTheoThang(nam, thang);
    }

    @Override
    public PageResponse<MonAnStatisticsResponse> topMonAnByThoiGian(LocalDate fromDate, LocalDate toDate, int page, int size) {
        LocalDateTime fromDateTime = fromDate.atStartOfDay();
        LocalDateTime toDateTime = toDate.atTime(23, 59, 59);

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<MonAnStatisticsResponse> p = chiTietHoaDonRepository.topMonAnByThoiGian(fromDateTime, toDateTime, pageRequest);

        return PageResponse.<MonAnStatisticsResponse>builder()
                .content(p.getContent())
                .page(p.getNumber())
                .size(p.getSize())
                .totalElements(p.getTotalElements())
                .totalPages(p.getTotalPages())
                .build();
    }

    @Override
    public List<NguyenLieuThongKeNgayResponse> thongKeNguyenLieuTheoNgay(LocalDate fromDate, LocalDate toDate) {
        LocalDateTime fromDateTime = fromDate.atStartOfDay();
        LocalDateTime toDateTime = toDate.atTime(23, 59, 59);
        return chiTietPhieuNhapRepository.thongKeNguyenLieuTheoNgay(fromDateTime, toDateTime);
    }

    @Override
    public List<NhaCungCapThongKeResponse> thongKeNhaCungCapTheoThang(int nam, int thang) {
        return chiTietPhieuNhapRepository.thongKeNhaCungCapTheoThang(nam, thang);
    }

    @Override
    public List<LuongNhanVienResponse> thongKeLuongNhanVienTheoThang(int nam, int thang) {
        // 1. Lấy tổng số khách (hóa đơn PAID) trong tháng
        Long tongKhach = hoaDonRepository.demTongKhachTrongThang(nam, thang);
        if (tongKhach == null) {
            tongKhach = 0L;
        }

        // ================= OLD LOGIC (theo nhân viên phục vụ thực tế) =================
        // 2. Lấy phân bổ số khách theo từng nhân viên
        // List<Object[]> raw = hoaDonRepository.demKhachTheoNhanVienTrongThang(nam, thang);
        //
        // List<LuongNhanVienResponse> result = new ArrayList<>();
        //
        // for (Object[] row : raw) {
        //     String maNhanVien = (String) row[0];
        //     String tenNhanVien = (String) row[1];
        //
        //     Long soKhachNhanVienPhucVu;
        //     if (row[2] instanceof Long) {
        //         soKhachNhanVienPhucVu = (Long) row[2];
        //     } else if (row[2] instanceof Number) {
        //         soKhachNhanVienPhucVu = ((Number) row[2]).longValue();
        //     } else {
        //         soKhachNhanVienPhucVu = 0L;
        //     }
        //
        //     Optional<NhanVien> nvOpt = nhanVienRepository.findById(maNhanVien);
        //     if (!nvOpt.isPresent()) {
        //         continue;
        //     }
        //     NhanVien nv = nvOpt.get();
        //     ChucVu chucVu = nv.getChucVu();
        //     if (chucVu == null) {
        //         continue;
        //     }
        //
        //     String maChucVu = chucVu.getMaChucVu();
        //     String tenChucVu = chucVu.getTenChucVu();
        //
        //     // Lấy lương cơ bản từ bảng luong thông qua quan hệ ChucVu.luong
        //     Luong luongEntity = chucVu.getLuong();
        //     BigDecimal luongCoBan = (luongEntity != null && luongEntity.getMucLuong() != null)
        //             ? luongEntity.getMucLuong()
        //             : BigDecimal.ZERO;
        //
        //     // Công thức thưởng: (Tổng khách / 10) * 2% * Lương cơ bản
        //     long soBlock10Khach = tongKhach / 10; // dùng tổng khách toàn nhà hàng trong tháng
        //     BigDecimal heSoThuong = new BigDecimal("0.02");
        //     BigDecimal thuong = luongCoBan
        //             .multiply(heSoThuong)
        //             .multiply(BigDecimal.valueOf(soBlock10Khach))
        //             .setScale(0, RoundingMode.HALF_UP);
        //
        //     // Lương thực nhận = Lương cơ bản + Thưởng
        //     BigDecimal luongThucNhan = luongCoBan.add(thuong);
        //
        //     result.add(new LuongNhanVienResponse(
        //             maNhanVien,
        //             tenNhanVien,
        //             maChucVu,
        //             tenChucVu,
        //             luongCoBan,
        //             soKhachNhanVienPhucVu,
        //             thuong,
        //             luongThucNhan
        //     ));
        // }

        // ================= NEW LOGIC: tính cho TẤT CẢ nhân viên đang tồn tại =================
        List<NhanVien> nhanViens = nhanVienRepository.findAll();
        List<LuongNhanVienResponse> result = new ArrayList<>();

        // Số block 10 khách dùng chung cho toàn bộ nhân viên
        long soBlock10Khach = tongKhach / 10; // dùng tổng khách toàn nhà hàng trong tháng
        BigDecimal heSoThuong = new BigDecimal("0.02");

        for (NhanVien nv : nhanViens) {
            if (nv == null) {
                continue;
            }
            ChucVu chucVu = nv.getChucVu();
            if (chucVu == null) {
                // Nếu nhân viên chưa gán chức vụ thì bỏ qua để tránh NullPointer
                continue;
            }

            String maNhanVien = nv.getMaNhanVien();
            String tenNhanVien = nv.getTenNhanVien();
            String maChucVu = chucVu.getMaChucVu();
            String tenChucVu = chucVu.getTenChucVu();

            // Lấy lương cơ bản từ bảng luong thông qua quan hệ ChucVu.luong
            Luong luongEntity = chucVu.getLuong();
            BigDecimal luongCoBan = (luongEntity != null && luongEntity.getMucLuong() != null)
                    ? luongEntity.getMucLuong()
                    : BigDecimal.ZERO;

            // Công thức thưởng: (Tổng khách / 10) * 2% * Lương cơ bản
            BigDecimal thuong = luongCoBan
                    .multiply(heSoThuong)
                    .multiply(BigDecimal.valueOf(soBlock10Khach))
                    .setScale(0, RoundingMode.HALF_UP);

            // Lương thực nhận = Lương cơ bản + Thưởng
            BigDecimal luongThucNhan = luongCoBan.add(thuong);

            // Theo yêu cầu mới: chỉ cần nhân viên tồn tại là trả ra, không phụ thuộc số khách nhân viên phục vụ
            Long soKhachNhanVienPhucVu = 0L;

            result.add(new LuongNhanVienResponse(
                    maNhanVien,
                    tenNhanVien,
                    maChucVu,
                    tenChucVu,
                    luongCoBan,
                    soKhachNhanVienPhucVu,
                    thuong,
                    luongThucNhan
            ));
        }

        return result;
    }

    @Override
    public List<ChiPhiNgayResponse> chiPhiTheoNgay(LocalDate fromDate, LocalDate toDate) {
        LocalDateTime fromDateTime = fromDate.atStartOfDay();
        LocalDateTime toDateTime = toDate.atTime(23, 59, 59);
        return chiTietPhieuNhapRepository.chiPhiTheoNgay(fromDateTime, toDateTime);
    }

    @Override
    public List<LoiNhuanNgayResponse> loiNhuanTheoNgay(LocalDate fromDate, LocalDate toDate) {
        // Lấy danh sách doanh thu theo ngày
        List<DoanhThuTheoNgayResponse> doanhThuList = doanhThuTheoNgay(fromDate, toDate);
        // Lấy danh sách chi phí theo ngày
        List<ChiPhiNgayResponse> chiPhiList = chiPhiTheoNgay(fromDate, toDate);

        // Đưa về map theo ngày để dễ kết hợp - dùng Date để tránh phải gọi toInstant() trên Date
        Map<Date, BigDecimal> doanhThuByDate = new HashMap<>();
        for (DoanhThuTheoNgayResponse d : doanhThuList) {
            Date ngayDate = d.getNgay();
            if (ngayDate == null) continue;
            doanhThuByDate.put(ngayDate, d.getDoanhThu());
        }

        Map<Date, BigDecimal> chiPhiByDate = new HashMap<>();
        for (ChiPhiNgayResponse c : chiPhiList) {
            Date ngayDate = c.getNgay();
            if (ngayDate == null) continue;
            chiPhiByDate.put(ngayDate, c.getTongChiPhi());
        }

        List<LoiNhuanNgayResponse> result = new ArrayList<>();

        LocalDate current = fromDate;
        while (!current.isAfter(toDate)) {
            // Convert LocalDate -> Date để tra trong map
            Date currentDate = Date.from(current.atStartOfDay(ZoneId.systemDefault()).toInstant());

            BigDecimal doanhThu = doanhThuByDate.getOrDefault(currentDate, BigDecimal.ZERO);
            BigDecimal chiPhi = chiPhiByDate.getOrDefault(currentDate, BigDecimal.ZERO);
            BigDecimal loiNhuan = doanhThu.subtract(chiPhi);

            // Trả về LocalDate trong response như cũ
            result.add(new LoiNhuanNgayResponse(current, doanhThu, chiPhi, loiNhuan));

            current = current.plusDays(1);
        }

        return result;
    }
}
