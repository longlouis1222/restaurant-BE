package vn.ptit.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.ptit.restaurant.entity.ChucVu;

public interface ChucVuRepository extends JpaRepository<ChucVu, String>, JpaSpecificationExecutor<ChucVu> {
}