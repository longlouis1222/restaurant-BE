package vn.ptit.restaurant.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChucVuResponse {
    private String maChucVu;
    private String tenChucVu;
    private String maLuong;
}
