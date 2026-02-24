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
@Table(name = "nhom_mon_an")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NhomMonAn {

    @Id
    @Column(name = "ma_nhom_mon")
    private String maNhomMon;

    @Column(nullable = false)
    private String tenNhomMon;

    private String moTa;
}