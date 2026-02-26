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
@Table(name = "nguyen_lieu")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NguyenLieu {

    @Id
    @Column(name = "ma_nguyen_lieu")
    private String maNguyenLieu;

    @Column(nullable = false)
    private String tenNguyenLieu;

    private String donViTinh;
    private java.math.BigDecimal giaNhap;
}