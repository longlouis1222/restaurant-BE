package vn.ptit.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.ptit.restaurant.entity.KhachHang;

public interface KhachHangRepository extends JpaRepository<KhachHang, String> {
}