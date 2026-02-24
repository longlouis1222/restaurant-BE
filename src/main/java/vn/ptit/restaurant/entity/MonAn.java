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
@Table(name = "mon_an")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonAn {

    @Id
    @Column(name = "ma_mon")
    private String maMon;

    @Column(nullable = false)
    private String tenMon;

    @Column(nullable = false)
    private java.math.BigDecimal donGia;

    private Integer thoiGianCheBien;

    @ManyToOne
    @JoinColumn(name = "ma_nhom_mon", nullable = false)
    private NhomMonAn nhomMonAn;
}