package vn.ptit.restaurant.common.catalog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaseSearchRequest {
    private String q;
    private int page = 0;
    private int size = 20;
    private String sortBy = "";
    private String sortDir = "asc";
}
