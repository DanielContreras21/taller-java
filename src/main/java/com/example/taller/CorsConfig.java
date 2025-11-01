package com.example.taller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // aplica a todas las rutas
                .allowedOrigins("*") // permite cualquier origen
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // métodos permitidos
                .allowedHeaders("*") // permite todos los headers
                .allowCredentials(false) // si se quieren cookies, poner true
                .maxAge(3600); // tiempo que el navegador puede cachear esta configuración
    }
}
