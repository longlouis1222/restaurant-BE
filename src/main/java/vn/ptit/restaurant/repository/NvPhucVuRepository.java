package vn.ptit.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.ptit.restaurant.entity.NvPhucVu;

public interface NvPhucVuRepository extends JpaRepository<NvPhucVu, String>, JpaSpecificationExecutor<NvPhucVu> {
}