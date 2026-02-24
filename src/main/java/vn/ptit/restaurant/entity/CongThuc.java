package vn.ptit.restaurant.entity;

import lombok.*;
import vn.ptit.restaurant.entity.id.CongThucId;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "cong_thuc")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CongThuc {

    @EmbeddedId
    private CongThucId id;

    @ManyToOne
    @MapsId("maMon")
    @JoinColumn(name = "ma_mon")
    private MonAn monAn;

    @ManyToOne
    @MapsId("maNguyenLieu")
    @JoinColumn(name = "ma_nguyen_lieu")
    private NguyenLieu nguyenLieu;

    private BigDecimal soLuong;
}