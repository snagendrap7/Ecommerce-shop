package com.dailycodework.Ecommerce_shop.dto;

import com.dailycodework.Ecommerce_shop.model.Image;
import lombok.Data;

@Data
public class ImageDto {

    Long imageId;

    String imageName;

    String downloadUrl;

}
