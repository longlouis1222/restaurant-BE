package vn.ptit.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.ptit.restaurant.entity.NvKhoBep;

public interface NvKhoBepRepository extends JpaRepository<NvKhoBep, String>, JpaSpecificationExecutor<NvKhoBep> {
}