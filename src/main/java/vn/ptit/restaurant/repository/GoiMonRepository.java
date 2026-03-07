package vn.ptit.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.ptit.restaurant.entity.GoiMon;
import vn.ptit.restaurant.entity.id.GoiMonId;

public interface GoiMonRepository extends JpaRepository<GoiMon, GoiMonId> {
}
