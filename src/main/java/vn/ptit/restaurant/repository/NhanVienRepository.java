package vn.ptit.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.ptit.restaurant.entity.NhanVien;

public interface NhanVienRepository extends JpaRepository<NhanVien, String> {
}