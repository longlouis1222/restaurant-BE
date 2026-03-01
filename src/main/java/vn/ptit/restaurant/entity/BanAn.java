package vn.ptit.restaurant.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ban_an")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BanAn {

    @Id
    @Column(name = "ma_ban")
    private String maBan;

    @Column(nullable = false)
    private Integer soCho;

    private String khuVuc;

    private Integer tanSuatSuDung;

    @Enumerated(EnumType.STRING)
    private TrangThaiBan trangThai;

    public enum TrangThaiBan {
        AVAILABLE,
        OCCUPIED
    }
}