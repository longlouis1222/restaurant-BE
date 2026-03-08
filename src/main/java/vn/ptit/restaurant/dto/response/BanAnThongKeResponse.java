package vn.ptit.restaurant.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BanAnThongKeResponse {

    // loại bàn: số lượng ghế, ví dụ 2, 4, 6
    private Integer soCho;

    // số bàn loại này được sử dụng trung bình mỗi ngày trong tháng
    private Double soBanDuocSuDungTrungBinhMoiNgay;
}
