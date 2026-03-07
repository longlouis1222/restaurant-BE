package vn.ptit.restaurant.entity.id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CungUngId implements Serializable {

    private String maNcc;
    private String maNguyenLieu;
}
