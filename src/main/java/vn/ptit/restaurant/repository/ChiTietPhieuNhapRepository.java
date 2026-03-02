package vn.ptit.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.ptit.restaurant.entity.ChiTietPhieuNhap;
import vn.ptit.restaurant.entity.id.ChiTietPhieuNhapId;

import java.util.List;

public interface ChiTietPhieuNhapRepository
        extends JpaRepository<ChiTietPhieuNhap, ChiTietPhieuNhapId> {

    List<ChiTietPhieuNhap> findByPhieuNhapMaPhieuNhap(String maPhieuNhap);

    void deleteByPhieuNhapMaPhieuNhap(String maPhieuNhap);
}