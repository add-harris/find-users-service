package com.example.findusersservice.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = AppConfig.APP_CONFIG_PREFIX)
public class AppConfig {

    static final String APP_CONFIG_PREFIX = "application";

    private String baseBackendUrl;
    private String cityEndpoint;
    private String usersEndpoint;

}
