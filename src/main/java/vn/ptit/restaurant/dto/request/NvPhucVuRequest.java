package vn.ptit.restaurant.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NvPhucVuRequest {
    @NotBlank(message = "maNhanVien is required")
    private String maNhanVien;

    @NotBlank(message = "khuVucPhuTrach is required")
    private String khuVucPhuTrach;

    @NotNull(message = "soLuongBanPhucVu is required")
    @Min(value = 0, message = "soLuongBanPhucVu must be >= 0")
    private Integer soLuongBanPhucVu;
}
