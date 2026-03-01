package vn.ptit.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.ptit.restaurant.entity.PhieuNhap;

public interface PhieuNhapRepository extends JpaRepository<PhieuNhap, String>, JpaSpecificationExecutor<PhieuNhap> {
}