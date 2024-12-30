package com.dailycodework.Ecommerce_shop.service.image;

import com.dailycodework.Ecommerce_shop.dto.ImageDto;
import com.dailycodework.Ecommerce_shop.exception.ImageProcessingException;
import com.dailycodework.Ecommerce_shop.exception.ResourceNotFoundException;
import com.dailycodework.Ecommerce_shop.model.Image;
import com.dailycodework.Ecommerce_shop.model.Product;
import com.dailycodework.Ecommerce_shop.repositery.ImageRepository;
import com.dailycodework.Ecommerce_shop.service.products.IProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
//import java.util.logging.Logger;


@Service
@RequiredArgsConstructor
public class ImageService implements IImageService{

    private final ImageRepository imageRepository;

    private final IProductService iProductService;

    private static final Logger logger = LoggerFactory.getLogger(ImageService.class);

    @Override
    @Transactional
    public Image getImageById(Long id) {
        return imageRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Image not Found!"));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete,()->{
            throw new ResourceNotFoundException("Resource Not found!");
        });
    }

    @Override
    public List<ImageDto> saveImage(List<MultipartFile> files, Long productId) {
        Product product =iProductService.getProductById(productId);

        List<ImageDto> savedImageDto = new ArrayList<>();

        for(MultipartFile file:files){
            try{
                Image image= new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String buildDownloadUrl="/api/v1/images/image/download/";
                String downloadUrl = buildDownloadUrl+image.getId();
                image.setDownloadUrl(downloadUrl);

                Image savedImage= imageRepository.save(image);

                savedImage.setDownloadUrl(buildDownloadUrl+savedImage.getId());

                imageRepository.save(savedImage);

                ImageDto imageDto=new ImageDto();
                imageDto.setImageId(savedImage.getId());
                imageDto.setImageName(savedImage.getFileName());
                imageDto.setDownloadUrl(savedImage.getDownloadUrl());
                savedImageDto.add(imageDto);

            }  catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return savedImageDto;


    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        }catch(IOException e){
            throw new RuntimeException(e.getMessage());
        } catch (SerialException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
