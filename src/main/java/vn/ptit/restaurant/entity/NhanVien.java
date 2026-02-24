package vn.ptit.restaurant.entity;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "nhan_vien")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NhanVien {

    @Id
    @Column(name = "ma_nhan_vien")
    private String maNhanVien;

    @Column(name = "ten_nhan_vien", nullable = false)
    private String tenNhanVien;

    @Column(name = "so_dien_thoai")
    private String soDienThoai;

    @Column(name = "ngay_sinh", nullable = false)
    private LocalDate ngaySinh;

    @Column(name = "ngay_vao_lam")
    private LocalDateTime ngayVaoLam;

    private String cccd;

    @ManyToOne
    @JoinColumn(name = "ma_chuc_vu", nullable = false)
    private ChucVu chucVu;
}