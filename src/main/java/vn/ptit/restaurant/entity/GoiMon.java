package vn.ptit.restaurant.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.ptit.restaurant.entity.id.GoiMonId;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "goi_mon")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoiMon {

    @EmbeddedId
    private GoiMonId id;

    @ManyToOne
    @MapsId("maKhachHang")
    @JoinColumn(name = "ma_khach_hang")
    private KhachHang khachHang;

    @ManyToOne
    @MapsId("maMon")
    @JoinColumn(name = "ma_mon")
    private MonAn monAn;

    @Column(name = "so_luong", nullable = false)
    private Integer soLuong;

    private String ghiChu;
}
