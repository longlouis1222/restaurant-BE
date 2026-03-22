package vn.ptit.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.ptit.restaurant.entity.Luong;

public interface LuongRepository extends JpaRepository<Luong, String>, JpaSpecificationExecutor<Luong> {
}
