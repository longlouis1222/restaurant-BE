package vn.ptit.restaurant.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import vn.ptit.restaurant.entity.BanAn;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BanAnRequest {
    private String maBan;

    @NotNull
    private Integer soCho;

    private String khuVuc;

    private Integer tanSuatSuDung;

    private BanAn.TrangThaiBan trangThai;
}
