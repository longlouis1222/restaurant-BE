package vn.ptit.restaurant.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NvPhucVuResponse {
    private String maNhanVien;
    private String khuVucPhuTrach;
    private Integer soLuongBanPhucVu;
}
