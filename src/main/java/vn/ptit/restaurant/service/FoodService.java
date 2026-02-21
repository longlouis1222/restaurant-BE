package vn.ptit.restaurant.service;

import vn.ptit.restaurant.dto.FoodRequest;
import vn.ptit.restaurant.dto.FoodResponse;

import java.util.List;

public interface FoodService {

    FoodResponse create(FoodRequest request);

    List<FoodResponse> getAll();

    FoodResponse getById(Long id);
}