package vn.ptit.restaurant.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.ptit.restaurant.common.catalog.AbstractCatalogService;
import vn.ptit.restaurant.dto.request.NhanVienRequest;
import vn.ptit.restaurant.dto.request.NhanVienSearchRequest;
import vn.ptit.restaurant.dto.response.NhanVienResponse;
import vn.ptit.restaurant.dto.response.NvKhoBepResponse;
import vn.ptit.restaurant.dto.response.NvPhucVuResponse;
import vn.ptit.restaurant.dto.response.NvThuNganResponse;
import vn.ptit.restaurant.entity.*;
import vn.ptit.restaurant.repository.NhanVienRepository;
import vn.ptit.restaurant.repository.NvKhoBepRepository;
import vn.ptit.restaurant.repository.NvPhucVuRepository;
import vn.ptit.restaurant.repository.NvThuNganRepository;
import vn.ptit.restaurant.service.NhanVienService;

@Service
@RequiredArgsConstructor
@Transactional
public class NhanVienServiceImpl extends AbstractCatalogService<
        NhanVien, String, NhanVienRequest, NhanVienRequest, NhanVienResponse, NhanVienSearchRequest>
        implements NhanVienService {

    private final NhanVienRepository repository;
    private final NvKhoBepRepository nvKhoBepRepository;
    private final NvPhucVuRepository nvPhucVuRepository;
    private final NvThuNganRepository nvThuNganRepository;

    @Override
    protected JpaRepository<NhanVien, String> getRepository() {
        return repository;
    }

    @Override
    protected JpaSpecificationExecutor<NhanVien> getSpecRepository() {
        return repository;
    }

    @Override
    protected String generateIdIfNeeded(NhanVienRequest request) {
        if (request.getMaNhanVien() != null && !request.getMaNhanVien().trim().isEmpty()) {
            return request.getMaNhanVien();
        }

        // Lấy 2 ký tự đầu từ mã chức vụ (nếu không có thì dùng "XX")
        String roleCode = "XX";
        if (request.getMaChucVu() != null && !request.getMaChucVu().trim().isEmpty()) {
            String maChucVu = request.getMaChucVu().trim();
            if (maChucVu.length() >= 2) {
                roleCode = maChucVu.substring(0, 2).toUpperCase();
            } else {
                roleCode = maChucVu.toUpperCase();
            }
        }

        String prefix = "NV" + roleCode; // ví dụ: NVPV, NVBT

        // Tìm số thứ tự lớn nhất hiện có cho prefix này, rồi +1
        Integer maxSuffix = repository.findMaxNumericSuffixByPrefix(prefix);
        int next = (maxSuffix == null ? 1 : maxSuffix + 1);

        String code = prefix + next; // ví dụ: NVPV1, NVBT11

        // Đảm bảo không vượt quá varchar(10)
        if (code.length() > 10) {
            // Nếu quá dài (số quá lớn), cắt bớt phần đầu số, giữ 10 ký tự cuối
            code = code.substring(code.length() - 10);
        }

        return code;
    }

    private enum RoleType {
        KHO_BEP,
        PHUC_VU,
        THU_NGAN,
        OTHER
    }

    /**
     * Xác định loại vai trò dựa trên mã nhân viên.
     * Mã NV được generate dạng: NV + 2 ký tự đầu mã chức vụ + số thứ tự
     * Ví dụ: NVPV1, NVKB10, NVTN3...
     */
    private RoleType determineRoleTypeFromMaNv(String maNhanVien) {
        if (maNhanVien == null) return RoleType.OTHER;
        String code = maNhanVien.trim().toUpperCase();
        if (!code.startsWith("NV") || code.length() < 4) {
            return RoleType.OTHER;
        }

        // Lấy 2 ký tự ngay sau "NV" làm mã vai trò
        String rolePart = code.substring(2, 4); // ví dụ: NVPV1 -> PV
        if (rolePart.equals("KB")) return RoleType.KHO_BEP;
        if (rolePart.equals("PV")) return RoleType.PHUC_VU;
        if (rolePart.equals("TN")) return RoleType.THU_NGAN;
        return RoleType.OTHER;
    }

    @Override
    protected NhanVien createEntity(NhanVienRequest request, String id) {
        NhanVien nhanVien = NhanVien.builder()
                .maNhanVien(id)
                .tenNhanVien(request.getTenNhanVien())
                .soDienThoai(request.getSoDienThoai())
                .ngaySinh(request.getNgaySinh())
                .ngayVaoLam(request.getNgayVaoLam())
                .cccd(request.getCccd())
                .chucVu(request.getMaChucVu() != null ? ChucVu.builder().maChucVu(request.getMaChucVu()).build() : null)
                .build();

        // Lưu NhanVien trước để có thể sử dụng trong các bảng con (MapsId)
        nhanVien = repository.save(nhanVien);

        // Tạo bản ghi chi tiết theo vai trò nếu có
        RoleType roleType = determineRoleTypeFromMaNv(nhanVien.getMaNhanVien());
        switch (roleType) {
            case KHO_BEP:
                if (request.getNvKhoBepInfo() != null) {
                    nvKhoBepRepository.save(NvKhoBep.builder()
                            .nhanVien(nhanVien)
                            .viTri(request.getNvKhoBepInfo().getViTri())
                            .trinhDo(request.getNvKhoBepInfo().getTrinhDo())
                            .build());
                }
                break;
            case PHUC_VU:
                if (request.getNvPhucVuInfo() != null) {
                    nvPhucVuRepository.save(NvPhucVu.builder()
                            .nhanVien(nhanVien)
                            .khuVucPhuTrach(request.getNvPhucVuInfo().getKhuVucPhuTrach())
                            .soLuongBanPhucVu(request.getNvPhucVuInfo().getSoLuongBanPhucVu())
                            .build());
                }
                break;
            case THU_NGAN:
                if (request.getNvThuNganInfo() != null) {
                    nvThuNganRepository.save(NvThuNgan.builder()
                            .nhanVien(nhanVien)
                            .caThuNgan(request.getNvThuNganInfo().getCaThuNgan())
                            .tongTienXuLy(request.getNvThuNganInfo().getTongTienXuLy())
                            .build());
                }
                break;
            default:
                // không tạo bản ghi chi tiết
                break;
        }

        return nhanVien;
    }

    @Override
    protected void applyUpdate(NhanVien entity, NhanVienRequest request) {
        RoleType oldRole = determineRoleTypeFromMaNv(entity.getMaNhanVien());

        if (request.getTenNhanVien() != null) entity.setTenNhanVien(request.getTenNhanVien());
        if (request.getSoDienThoai() != null) entity.setSoDienThoai(request.getSoDienThoai());
        if (request.getNgaySinh() != null) entity.setNgaySinh(request.getNgaySinh());
        if (request.getNgayVaoLam() != null) entity.setNgayVaoLam(request.getNgayVaoLam());
        if (request.getCccd() != null) entity.setCccd(request.getCccd());
        if (request.getMaChucVu() != null) entity.setChucVu(ChucVu.builder().maChucVu(request.getMaChucVu()).build());

        // Sau khi cập nhật, mã NV không đổi nên vai trò theo mã NV không đổi
        RoleType newRole = oldRole;

        // Đồng bộ thông tin bảng con theo vai trò
        if (oldRole != newRole) {
            nvKhoBepRepository.deleteById(entity.getMaNhanVien());
            nvPhucVuRepository.deleteById(entity.getMaNhanVien());
            nvThuNganRepository.deleteById(entity.getMaNhanVien());
        }

        switch (newRole) {
            case KHO_BEP:
                if (request.getNvKhoBepInfo() != null) {
                    NvKhoBep nv = nvKhoBepRepository.findById(entity.getMaNhanVien())
                            .orElse(NvKhoBep.builder().maNhanVien(entity.getMaNhanVien()).nhanVien(entity).build());
                    nv.setViTri(request.getNvKhoBepInfo().getViTri());
                    nv.setTrinhDo(request.getNvKhoBepInfo().getTrinhDo());
                    nvKhoBepRepository.save(nv);
                }
                break;
            case PHUC_VU:
                if (request.getNvPhucVuInfo() != null) {
                    NvPhucVu nv = nvPhucVuRepository.findById(entity.getMaNhanVien())
                            .orElse(NvPhucVu.builder().maNhanVien(entity.getMaNhanVien()).nhanVien(entity).build());
                    nv.setKhuVucPhuTrach(request.getNvPhucVuInfo().getKhuVucPhuTrach());
                    nv.setSoLuongBanPhucVu(request.getNvPhucVuInfo().getSoLuongBanPhucVu());
                    nvPhucVuRepository.save(nv);
                }
                break;
            case THU_NGAN:
                if (request.getNvThuNganInfo() != null) {
                    NvThuNgan nv = nvThuNganRepository.findById(entity.getMaNhanVien())
                            .orElse(NvThuNgan.builder().maNhanVien(entity.getMaNhanVien()).nhanVien(entity).build());
                    nv.setCaThuNgan(request.getNvThuNganInfo().getCaThuNgan());
                    nv.setTongTienXuLy(request.getNvThuNganInfo().getTongTienXuLy());
                    nvThuNganRepository.save(nv);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected NhanVienResponse toResponse(NhanVien entity) {
        NhanVienResponse.NhanVienResponseBuilder builder = NhanVienResponse.builder()
                .maNhanVien(entity.getMaNhanVien())
                .tenNhanVien(entity.getTenNhanVien())
                .soDienThoai(entity.getSoDienThoai())
                .ngaySinh(entity.getNgaySinh())
                .ngayVaoLam(entity.getNgayVaoLam())
                .cccd(entity.getCccd())
                .maChucVu(entity.getChucVu() != null ? entity.getChucVu().getMaChucVu() : null);

        String maNv = entity.getMaNhanVien();
        nvKhoBepRepository.findById(maNv).ifPresent(kb -> builder.nvKhoBepInfo(NvKhoBepResponse.builder()
                .maNhanVien(kb.getMaNhanVien())
                .viTri(kb.getViTri())
                .trinhDo(kb.getTrinhDo())
                .build()));
        nvPhucVuRepository.findById(maNv).ifPresent(pv -> builder.nvPhucVuInfo(NvPhucVuResponse.builder()
                .maNhanVien(pv.getMaNhanVien())
                .khuVucPhuTrach(pv.getKhuVucPhuTrach())
                .soLuongBanPhucVu(pv.getSoLuongBanPhucVu())
                .build()));
        nvThuNganRepository.findById(maNv).ifPresent(tn -> builder.nvThuNganInfo(NvThuNganResponse.builder()
                .maNhanVien(tn.getMaNhanVien())
                .caThuNgan(tn.getCaThuNgan())
                .tongTienXuLy(tn.getTongTienXuLy())
                .build()));

        return builder.build();
    }

    @Override
    protected String[] getSearchFields() {
        return new String[]{"maNhanVien", "tenNhanVien"};
    }
}
