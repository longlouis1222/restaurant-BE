package vn.ptit.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.ptit.restaurant.entity.NvThuNgan;

public interface NvThuNganRepository extends JpaRepository<NvThuNgan, String>, JpaSpecificationExecutor<NvThuNgan> {
}