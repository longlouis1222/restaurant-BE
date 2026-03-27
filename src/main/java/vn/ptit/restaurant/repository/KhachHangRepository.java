package vn.ptit.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.ptit.restaurant.entity.KhachHang;

import java.util.Optional;

public interface KhachHangRepository extends JpaRepository<KhachHang, String>, JpaSpecificationExecutor<KhachHang> {
    Optional<KhachHang> findBySdt(String sdt);
}