package com.upc.unistress.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "dzgdttwjj");
        config.put("api_key", "463365214578544");
        config.put("api_secret", "cZCtZeEWaaC3vxINLumVy1cHit4");
        return new Cloudinary(config);
    }
}

