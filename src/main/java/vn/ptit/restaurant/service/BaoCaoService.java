package vn.ptit.restaurant.service;

import vn.ptit.restaurant.dto.*;
import vn.ptit.restaurant.dto.response.DoanhThuResponse;
import vn.ptit.restaurant.dto.response.DoanhThuTheoNgayResponse;
import vn.ptit.restaurant.dto.response.DoanhThuTheoThangResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface BaoCaoService {

    DoanhThuResponse tinhTongDoanhThu(LocalDateTime tuNgay, LocalDateTime denNgay);

    List<DoanhThuTheoNgayResponse> doanhThuTheoNgay(LocalDateTime tuNgay, LocalDateTime denNgay);

    List<DoanhThuTheoThangResponse> doanhThuTheoThang(int nam);
}