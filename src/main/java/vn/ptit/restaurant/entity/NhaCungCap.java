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
@Table(name = "nha_cung_cap")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NhaCungCap {

    @Id
    @Column(name = "ma_ncc")
    private String maNcc;

    @Column(nullable = false)
    private String tenNcc;

    private String thongTin;
}