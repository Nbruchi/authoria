package com.bruce.auth.services;

import com.bruce.auth.config.CloudinaryConfig;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
public class ImageService {
    private final Cloudinary cloudinary;

    public ImageService(CloudinaryConfig config){
        this.cloudinary = config.getCloudinary();
    }

    @SuppressWarnings("unchecked")
    public String uploadImage(File file)throws IOException {
        Map<String, String> uploadResult = cloudinary.uploader().upload(file, ObjectUtils.asMap(
                "resource_type", "image"
        ));

        return (String) uploadResult.get("public_id");
    }

    public Map getImage(String publicId) throws IOException, Exception {
        return cloudinary.api().resource(publicId, ObjectUtils.asMap("resource_type", "image"));
    }

    public String updateImage(String publicId, File image)throws IOException {
       Map options = ObjectUtils.asMap(
               "public_id", publicId,
               "resource_type", "image"
       );

       Map uploadResult = cloudinary.uploader().upload(image,options);
       return (String) uploadResult.get("public_id");
    }

    public void deleteImage(String publicId)throws IOException{
        Map options = ObjectUtils.asMap(
                "public_id", publicId,
                "resource_type", "image"
        );

        cloudinary.uploader().destroy(publicId,options);
    }
}
