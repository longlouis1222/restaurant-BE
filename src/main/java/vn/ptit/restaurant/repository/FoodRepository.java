package vn.ptit.restaurant.repository;

import vn.ptit.restaurant.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long> {
}