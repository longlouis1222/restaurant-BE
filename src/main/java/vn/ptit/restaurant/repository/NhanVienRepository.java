package vn.ptit.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.ptit.restaurant.entity.NhanVien;

public interface NhanVienRepository extends JpaRepository<NhanVien, String>, JpaSpecificationExecutor<NhanVien> {
    @Query("SELECT MAX(CAST(SUBSTRING(n.maNhanVien, LENGTH(:prefix) + 1) AS integer)) FROM NhanVien n WHERE n.maNhanVien LIKE CONCAT(:prefix, '%')")
    Integer findMaxNumericSuffixByPrefix(@Param("prefix") String prefix);
}