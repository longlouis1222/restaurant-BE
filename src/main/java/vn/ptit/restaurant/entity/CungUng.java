package vn.ptit.restaurant.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.ptit.restaurant.entity.id.CungUngId;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "cung_ung")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CungUng {

    @EmbeddedId
    private CungUngId id;

    @ManyToOne
    @MapsId("maNcc")
    @JoinColumn(name = "ma_ncc")
    private NhaCungCap nhaCungCap;

    @ManyToOne
    @MapsId("maNguyenLieu")
    @JoinColumn(name = "ma_nguyen_lieu")
    private NguyenLieu nguyenLieu;

    @Column(name = "gia_cung_cap")
    private BigDecimal giaCungCap;

    @Column(name = "thoi_diem_hop_tac")
    private LocalDateTime thoiDiemHopTac;
}
