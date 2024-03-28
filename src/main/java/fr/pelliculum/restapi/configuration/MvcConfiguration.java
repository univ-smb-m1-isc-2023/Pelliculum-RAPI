package fr.pelliculum.restapi.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Assurez-vous que le chemin du dossier externe est correct et accessible
        // Exemple: "file:///C:/profilePictures/" sous Windows ou "file:/profilePictures/" sous Linux
        registry.addResourceHandler("/profilePictures/**")
                .addResourceLocations("file:/profilePictures/");
    }

}
