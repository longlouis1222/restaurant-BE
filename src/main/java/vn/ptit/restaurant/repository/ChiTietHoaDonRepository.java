package vn.ptit.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.ptit.restaurant.entity.ChiTietHoaDon;
import vn.ptit.restaurant.entity.id.ChiTietHoaDonId;

public interface ChiTietHoaDonRepository
        extends JpaRepository<ChiTietHoaDon, ChiTietHoaDonId> {
}