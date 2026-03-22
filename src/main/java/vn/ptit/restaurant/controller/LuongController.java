package vn.ptit.restaurant.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.ptit.restaurant.common.ApiResponse;
import vn.ptit.restaurant.common.catalog.AbstractCatalogController;
import vn.ptit.restaurant.dto.request.LuongRequest;
import vn.ptit.restaurant.dto.request.LuongSearchRequest;
import vn.ptit.restaurant.dto.response.LuongNhanVienResponse;
import vn.ptit.restaurant.dto.response.LuongResponse;
import vn.ptit.restaurant.service.LuongService;
import vn.ptit.restaurant.service.NhanVienService;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/luong")
public class LuongController extends AbstractCatalogController<String, LuongRequest, LuongRequest, LuongResponse, LuongSearchRequest> {

    private final NhanVienService nhanVienService;

    public LuongController(LuongService luongService, NhanVienService nhanVienService) {
        super(luongService);
        this.nhanVienService = nhanVienService;
    }

    @GetMapping("/nhan-vien")
    public ApiResponse<List<LuongNhanVienResponse>> getLuongNhanVien() {
        // TODO: Implement real logic when NhanVienService has salary method.
        return ApiResponse.<List<LuongNhanVienResponse>>builder()
                .success(true)
                .message("Danh sách lương nhân viên")
                .data(Arrays.asList())
                .build();
    }
}
