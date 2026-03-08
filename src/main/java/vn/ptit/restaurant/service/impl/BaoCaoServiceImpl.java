package vn.ptit.restaurant.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.ptit.restaurant.dto.response.BanAnThongKeResponse;
import vn.ptit.restaurant.dto.response.DoanhThuResponse;
import vn.ptit.restaurant.dto.response.DoanhThuTheoThangResponse;
import vn.ptit.restaurant.repository.HoaDonRepository;
import vn.ptit.restaurant.service.BaoCaoService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BaoCaoServiceImpl implements BaoCaoService {

    private final HoaDonRepository hoaDonRepository;

    @Override
    public DoanhThuResponse tinhTongDoanhThu(LocalDateTime tuNgay, LocalDateTime denNgay) {
        BigDecimal tong = hoaDonRepository.tinhTongDoanhThu(tuNgay, denNgay);
        return new DoanhThuResponse(tong);
    }

    @Override
    public List<DoanhThuTheoThangResponse> doanhThuTheoThang(int nam) {
        return hoaDonRepository.doanhThuTheoThang(nam);
    }

    @Override
    public List<BanAnThongKeResponse> thongKeBanAnTrungBinhTheoNgayTrongThang(int nam, int thang) {
        // Xác định khoảng thời gian đầu - cuối tháng
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
}