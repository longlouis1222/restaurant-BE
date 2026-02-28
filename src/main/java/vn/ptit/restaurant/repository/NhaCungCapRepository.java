package vn.ptit.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.ptit.restaurant.entity.NhaCungCap;

public interface NhaCungCapRepository extends JpaRepository<NhaCungCap, String>, JpaSpecificationExecutor<NhaCungCap> {
}