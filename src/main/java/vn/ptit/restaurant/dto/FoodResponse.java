package vn.ptit.restaurant.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FoodResponse {

    private Long id;
    private String name;
    private Double price;
    private String imageUrl;
    private Boolean available;
}