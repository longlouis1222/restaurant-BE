package vn.ptit.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.ptit.restaurant.entity.CungUng;
import vn.ptit.restaurant.entity.id.CungUngId;

public interface CungUngRepository extends JpaRepository<CungUng, CungUngId> {
}
