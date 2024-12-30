package com.dailycodework.Ecommerce_shop.controller;

import com.dailycodework.Ecommerce_shop.dto.ImageDto;
import com.dailycodework.Ecommerce_shop.exception.ResourceNotFoundException;
import com.dailycodework.Ecommerce_shop.model.Image;
import com.dailycodework.Ecommerce_shop.response.ApiResponse;
import com.dailycodework.Ecommerce_shop.service.image.IImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping(value="${api.prefix}/images")
@RequiredArgsConstructor
public class ImageController {

    private final IImageService iImageService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> saveImages(@RequestParam List<MultipartFile> files, @RequestParam Long productId){
        try {
            List<ImageDto> imaageDtos = iImageService.saveImage(files, productId);
            return ResponseEntity.ok(new ApiResponse("Uploaded Image", imaageDtos));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("upload failed!",e.getMessage()));
        }
    }

    @GetMapping("/image/download/{imageId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) throws SQLException {
        Image image=iImageService.getImageById(imageId);
        ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1,(int) image.getImage().length()));
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+image.getFileName()+"\"")
                .body(resource);
//        ByteArrayResource resource = new ByteArrayResource(image.getImage());
//
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType(image.getFileType()))
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
//                .body(resource);
    }

    @PutMapping("/image/{imageId}/update")
    public ResponseEntity<ApiResponse> updateImage(@PathVariable Long imageId, @RequestBody MultipartFile file){
        try{
            Image image=iImageService.getImageById((imageId));
            if(image!=null){
                iImageService.updateImage(file,imageId);
                return ResponseEntity.ok(new ApiResponse("update success!",null));
            }
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Update failed!",HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @DeleteMapping("/image/{imageId}/delete")
    public ResponseEntity<ApiResponse> updateImage(@PathVariable Long imageId){
        try{
            Image image=iImageService.getImageById((imageId));
            if(image!=null){
                iImageService.deleteImageById(imageId);
                return ResponseEntity.ok(new ApiResponse("Delete success!",null));
            }
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Delete failed!",HttpStatus.INTERNAL_SERVER_ERROR));
    }


}
