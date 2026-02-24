package vn.ptit.restaurant.entity;

import lombok.*;
import vn.ptit.restaurant.entity.id.ChiTietPhieuNhapId;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "chi_tiet_phieu_nhap")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChiTietPhieuNhap {

    @EmbeddedId
    private ChiTietPhieuNhapId id;

    @ManyToOne
    @MapsId("maPhieuNhap")
    @JoinColumn(name = "ma_phieu_nhap")
    private PhieuNhap phieuNhap;

    @ManyToOne
    @MapsId("maNguyenLieu")
    @JoinColumn(name = "ma_nguyen_lieu")
    private NguyenLieu nguyenLieu;

    private Integer soLuong;
    private BigDecimal donGia;
}