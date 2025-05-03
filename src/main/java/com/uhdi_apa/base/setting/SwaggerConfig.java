package com.uhdi_apa.base.setting;

import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
            .components(new Components())
            .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
            .title("어디아파 Server API") // API의 제목
            .description("어디아파 명세서") // API에 대한 설명
            .version("1.0.0"); // API의 버전
    }

}