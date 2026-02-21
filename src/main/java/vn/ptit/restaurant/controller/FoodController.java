package vn.ptit.restaurant.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vn.ptit.restaurant.dto.FoodRequest;
import vn.ptit.restaurant.payload.ApiResponse;
import vn.ptit.restaurant.service.FoodService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/foods")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService service;

    @PostMapping
    public ApiResponse<?> create(@Valid @RequestBody FoodRequest request) {
        return ApiResponse.success(service.create(request), "Created successfully");
    }

    @GetMapping
    public ApiResponse<?> getAll() {
        return ApiResponse.success(service.getAll(), "Get all successfully");
    }

    @GetMapping("/{id}")
    public ApiResponse<?> getById(@PathVariable Long id) {
        return ApiResponse.success(service.getById(id), "Get by id successfully");
    }
}