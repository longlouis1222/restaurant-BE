package vn.ptit.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.ptit.restaurant.entity.NguyenLieu;

public interface NguyenLieuRepository extends JpaRepository<NguyenLieu, String>, JpaSpecificationExecutor<NguyenLieu> {
}