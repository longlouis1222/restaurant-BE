package vn.ptit.restaurant.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "nv_thu_ngan")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NvThuNgan {

    @Id
    @Column(name = "ma_nhan_vien")
    private String maNhanVien;

    @OneToOne
    @MapsId
    @JoinColumn(name = "ma_nhan_vien")
    private NhanVien nhanVien;

    private String caThuNgan;
    private java.math.BigDecimal tongTienXuLy;
}