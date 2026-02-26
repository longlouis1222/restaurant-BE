package vn.ptit.restaurant.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.ptit.restaurant.entity.id.ChiTietHoaDonId;

import javax.persistence.*;

@Entity
@Table(name = "chi_tiet_hoa_don")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChiTietHoaDon {

    @EmbeddedId
    private ChiTietHoaDonId id;

    @ManyToOne
    @MapsId("maHoaDon")
    @JoinColumn(name = "ma_hoa_don")
    private HoaDon hoaDon;

    @ManyToOne
    @MapsId("maMon")
    @JoinColumn(name = "ma_mon")
    private MonAn monAn;

    private Integer soLuong;
    private java.math.BigDecimal donGia;
}