package vn.ptit.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.ptit.restaurant.entity.BanAn;

public interface BanAnRepository extends JpaRepository<BanAn, String>, JpaSpecificationExecutor<BanAn> {
}