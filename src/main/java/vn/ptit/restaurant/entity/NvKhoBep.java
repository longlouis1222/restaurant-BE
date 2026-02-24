package vn.ptit.restaurant.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "nv_kho_bep")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NvKhoBep {

    @Id
    @Column(name = "ma_nhan_vien")
    private String maNhanVien;

    @OneToOne
    @MapsId
    @JoinColumn(name = "ma_nhan_vien")
    private NhanVien nhanVien;

    private String viTri;
    private String trinhDo;
}