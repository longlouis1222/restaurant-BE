package vn.ptit.restaurant.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.ptit.restaurant.dto.*;
import vn.ptit.restaurant.dto.response.DoanhThuResponse;
import vn.ptit.restaurant.dto.response.DoanhThuTheoNgayResponse;
import vn.ptit.restaurant.dto.response.DoanhThuTheoThangResponse;
import vn.ptit.restaurant.repository.HoaDonRepository;
import vn.ptit.restaurant.service.BaoCaoService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    public List<DoanhThuTheoNgayResponse> doanhThuTheoNgay(LocalDateTime tuNgay, LocalDateTime denNgay) {

        return hoaDonRepository.doanhThuTheoNgay(tuNgay, denNgay);
    }

    @Override
    public List<DoanhThuTheoThangResponse> doanhThuTheoThang(int nam) {

        return hoaDonRepository.doanhThuTheoThang(nam);
    }
}