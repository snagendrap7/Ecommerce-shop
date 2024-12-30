package com.dailycodework.Ecommerce_shop.response;

import com.dailycodework.Ecommerce_shop.dto.ImageDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class ApiResponse {

    private String message;

    private Object data;

    public ApiResponse(String message, Object data) {
        this.message = message;
        this.data = data;
    }
}
