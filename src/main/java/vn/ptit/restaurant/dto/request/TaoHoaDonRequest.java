package vn.ptit.restaurant.dto.request;

import lombok.*;
import java.util.List;

@Data
public class TaoHoaDonRequest {

    private String banId;

    private List<ChiTietRequest> chiTietList;

    @Data
    public static class ChiTietRequest {
        private String monAnId;
        private Integer soLuong;
    }
}