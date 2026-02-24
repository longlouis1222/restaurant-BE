package vn.ptit.restaurant.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "khach_hang")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KhachHang {

    @Id
    @Column(name = "ma_khach_hang")
    private String maKhachHang;

    @Column(nullable = false)
    private String tenKhachHang;

    private String sdt;
    private String diaChi;
    private java.time.LocalDate ngaySinh;

    private java.math.BigDecimal diemTichLuy;
}