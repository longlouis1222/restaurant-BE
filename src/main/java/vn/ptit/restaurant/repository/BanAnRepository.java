package vn.ptit.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.ptit.restaurant.entity.BanAn;

public interface BanAnRepository extends JpaRepository<BanAn, String> {
}