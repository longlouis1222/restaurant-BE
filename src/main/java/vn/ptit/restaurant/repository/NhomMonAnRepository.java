package vn.ptit.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.ptit.restaurant.entity.NhomMonAn;

public interface NhomMonAnRepository extends JpaRepository<NhomMonAn, String>, JpaSpecificationExecutor<NhomMonAn> {
}