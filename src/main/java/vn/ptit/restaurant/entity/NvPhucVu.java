package vn.ptit.restaurant.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "nv_phuc_vu")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NvPhucVu {

    @Id
    @Column(name = "ma_nhan_vien")
    private String maNhanVien;

    @OneToOne
    @MapsId
    @JoinColumn(name = "ma_nhan_vien")
    private NhanVien nhanVien;

    private String khuVucPhuTrach;
    private Integer soLuongBanPhucVu;
}