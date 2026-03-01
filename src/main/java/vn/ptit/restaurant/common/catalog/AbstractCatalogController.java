package vn.ptit.restaurant.common.catalog;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.ptit.restaurant.common.ApiResponse;
import vn.ptit.restaurant.dto.response.PageResponse;

import java.util.List;

/**
 * Generic controller for catalog endpoints. Subclasses should provide @RequestMapping on subclass
 * and pass a service implementing CatalogServiceContract.
 */
public abstract class AbstractCatalogController<ID, CreateReq, UpdateReq, Resp, SearchReq extends BaseSearchRequest> {

    protected final CatalogServiceContract<?, ID, CreateReq, UpdateReq, Resp, SearchReq> service;

    protected AbstractCatalogController(CatalogServiceContract<?, ID, CreateReq, UpdateReq, Resp, SearchReq> service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Resp>> create(@Validated @RequestBody CreateReq request) {
        Resp res = service.create(request);
        return ResponseEntity.ok(ApiResponse.<Resp>builder().success(true).message("Tạo thành công").data(res).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Resp>> update(@PathVariable("id") ID id, @Validated @RequestBody UpdateReq request) {
        Resp res = service.update(id, request);
        return ResponseEntity.ok(ApiResponse.<Resp>builder().success(true).message("Cập nhật thành công").data(res).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> delete(@PathVariable("id") ID id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.builder().success(true).message("Xóa thành công").build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Resp>> getById(@PathVariable("id") ID id) {
        Resp res = service.getById(id);
        return ResponseEntity.ok(ApiResponse.<Resp>builder().success(true).data(res).build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Resp>>> getAll() {
        List<Resp> list = service.getAll();
        return ResponseEntity.ok(ApiResponse.<List<Resp>>builder().success(true).data(list).build());
    }

    @PostMapping("/search")
    public ResponseEntity<ApiResponse<PageResponse<Resp>>> search(@Validated @RequestBody SearchReq request) {
        PageResponse<Resp> page = service.search(request);
        return ResponseEntity.ok(ApiResponse.<PageResponse<Resp>>builder().success(true).data(page).build());
    }
}
