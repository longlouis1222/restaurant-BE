package vn.ptit.restaurant.repository;

import vn.ptit.restaurant.entity.NvPhucVu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<NvPhucVu, Long> {
}