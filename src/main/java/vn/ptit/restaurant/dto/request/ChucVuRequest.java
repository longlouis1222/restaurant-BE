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
public class ChucVuRequest {
    private String maChucVu;

    @NotBlank
    private String tenChucVu;

    @NotBlank
    private String maLuong;
}
