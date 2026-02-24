package vn.ptit.restaurant.entity.id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CongThucId implements java.io.Serializable {

    private String maMon;
    private String maNguyenLieu;
}
