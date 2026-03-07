package vn.ptit.restaurant.entity.id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoiMonId implements Serializable {

    private String maKhachHang;
    private String maMon;
    private LocalDateTime thoiDiemGoi;
}
