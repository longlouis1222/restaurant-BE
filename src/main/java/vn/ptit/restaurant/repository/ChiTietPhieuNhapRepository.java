package vn.ptit.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.ptit.restaurant.entity.ChiTietPhieuNhap;
import vn.ptit.restaurant.entity.id.ChiTietPhieuNhapId;

public interface ChiTietPhieuNhapRepository
        extends JpaRepository<ChiTietPhieuNhap, ChiTietPhieuNhapId> {
}