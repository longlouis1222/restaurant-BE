package vn.ptit.restaurant.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.ptit.restaurant.dto.FoodRequest;
import vn.ptit.restaurant.dto.FoodResponse;
import vn.ptit.restaurant.entity.NvPhucVu;
import vn.ptit.restaurant.exception.ResourceNotFoundException;
import vn.ptit.restaurant.repository.FoodRepository;
import vn.ptit.restaurant.service.FoodService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {

    private final FoodRepository repository;

    @Override
    public FoodResponse create(FoodRequest request) {
        NvPhucVu NVPhucVu = NVPhucVu.builder()
                .name(request.getName())
                .price(request.getPrice())
                .imageUrl(request.getImageUrl())
                .available(true)
                .build();

        NvPhucVu saved = repository.save(NVPhucVu);

        return mapToResponse(saved);
    }

    @Override
    public List<FoodResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public FoodResponse getById(Long id) {
        NvPhucVu NVPhucVu = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Food not found"));

        return mapToResponse(NVPhucVu);
    }

    private FoodResponse mapToResponse(NvPhucVu NVPhucVu) {
        return FoodResponse.builder()
                .id(NVPhucVu.getId())
                .name(NVPhucVu.getName())
                .price(NVPhucVu.getPrice())
                .imageUrl(NVPhucVu.getImageUrl())
                .available(NVPhucVu.getAvailable())
                .build();
    }
}