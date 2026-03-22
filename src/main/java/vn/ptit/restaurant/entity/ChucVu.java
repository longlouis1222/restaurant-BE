package vn.ptit.restaurant.entity;

import lombok.*;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "chuc_vu")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChucVu {

    @Id
    @Column(name = "ma_chuc_vu")
    private String maChucVu;

    @Column(name = "ten_chuc_vu", nullable = false)
    private String tenChucVu;

    // N ChucVu - 1 Luong
    @ManyToOne
    @JoinColumn(name = "ma_luong", nullable = false)
    private Luong luong;

    @OneToMany(mappedBy = "chucVu")
    private List<NhanVien> nhanViens;
}