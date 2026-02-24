package vn.ptit.restaurant.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "phieu_nhap")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhieuNhap {

    @Id
    @Column(name = "ma_phieu_nhap")
    private String maPhieuNhap;

    @ManyToOne
    @JoinColumn(name = "ma_ncc")
    private NhaCungCap nhaCungCap;

    @ManyToOne
    @JoinColumn(name = "ma_nhan_vien")
    private NhanVien nhanVien;

    private java.time.LocalDateTime ngayNhap;
}