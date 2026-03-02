package vn.ptit.restaurant.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NvKhoBepRequest {
    @NotBlank(message = "maNhanVien is required")
    private String maNhanVien;

    @NotBlank(message = "viTri is required")
    private String viTri;

    @NotBlank(message = "trinhDo is required")
    private String trinhDo;
}
