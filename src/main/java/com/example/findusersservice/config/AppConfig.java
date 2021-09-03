package com.example.findusersservice.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = AppConfig.APP_CONFIG_PREFIX)
public class AppConfig {

    static final String APP_CONFIG_PREFIX = "application";

    private String baseBackendUrl;

}
