package vn.ptit.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.ptit.restaurant.entity.MonAn;

public interface MonAnRepository extends JpaRepository<MonAn, String>, JpaSpecificationExecutor<MonAn> {
}