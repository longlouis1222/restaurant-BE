package vn.ptit.restaurant.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.ptit.restaurant.dto.FoodRequest;
import vn.ptit.restaurant.dto.FoodResponse;
import vn.ptit.restaurant.entity.Food;
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
        Food food = Food.builder()
                .name(request.getName())
                .price(request.getPrice())
                .imageUrl(request.getImageUrl())
                .available(true)
                .build();

        Food saved = repository.save(food);

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
        Food food = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Food not found"));

        return mapToResponse(food);
    }

    private FoodResponse mapToResponse(Food food) {
        return FoodResponse.builder()
                .id(food.getId())
                .name(food.getName())
                .price(food.getPrice())
                .imageUrl(food.getImageUrl())
                .available(food.getAvailable())
                .build();
    }
}