package vn.ptit.restaurant.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "luong")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Luong {

    @Id
    @Column(name = "ma_luong")
    private String maLuong;

    @Column(name = "muc_luong", nullable = false, precision = 18, scale = 2)
    private BigDecimal mucLuong;

    @Column(name = "ngay_cong_theo_thang")
    private Integer ngayCongTheoThang;

    @Column(name = "ngay_cong_thuc_te")
    private Integer ngayCongThucTe;

    @Column(name = "thuong", precision = 18, scale = 2)
    private BigDecimal thuong;

    @Column(name = "phat")
    private Integer phat;

    @Column(name = "ghi_chu", length = 50)
    private String ghiChu;

    // 1 Luong - N ChucVu
    @OneToMany(mappedBy = "luong")
    private List<ChucVu> chucVus;
}
