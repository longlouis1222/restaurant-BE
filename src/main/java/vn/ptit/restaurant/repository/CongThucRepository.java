package vn.ptit.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.ptit.restaurant.entity.CongThuc;
import vn.ptit.restaurant.entity.id.CongThucId;

public interface CongThucRepository
        extends JpaRepository<CongThuc, CongThucId> {
}