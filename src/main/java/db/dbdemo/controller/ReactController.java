package db.dbdemo.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ReactController implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/{variable:\\w+}")
                .setViewName("forward:/");
        registry.addViewController("/**/{variable:\\w+}")
                .setViewName("forward:/");
        registry.addViewController("/{variable:\\w+}/**{variable:?!(\\.js|\\.css)$}")
                .setViewName("forward:/");
    }
}