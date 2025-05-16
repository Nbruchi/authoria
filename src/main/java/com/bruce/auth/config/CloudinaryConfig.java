package com.bruce.auth.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {
    private final Cloudinary cloudinary;

    public CloudinaryConfig(){
        cloudinary = new Cloudinary(
                ObjectUtils.asMap(
                        "cloud_name", "dcwbaeopv",
                        "api_key", "829738157242999",
                        "api_secret", "T5y9SGQkRmNQowGazHqKOaze3K8",
                        "upload_prefix", "authoria"
                )
        );
    }

    public Cloudinary getCloudinary(){
        return cloudinary;
    }
}
