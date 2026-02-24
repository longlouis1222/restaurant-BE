package vn.ptit.restaurant.entity.id;

import lombok.*;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietPhieuNhapId implements Serializable {

    private String maPhieuNhap;
    private String maNguyenLieu;
}