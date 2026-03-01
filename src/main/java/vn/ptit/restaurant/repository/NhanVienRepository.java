package vn.ptit.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.ptit.restaurant.entity.NhanVien;

public interface NhanVienRepository extends JpaRepository<NhanVien, String>, JpaSpecificationExecutor<NhanVien> {
}