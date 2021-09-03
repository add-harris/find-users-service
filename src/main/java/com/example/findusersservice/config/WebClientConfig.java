package com.example.findusersservice.config;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;

@Configuration
@Slf4j
public class WebClientConfig {

    private AppConfig appConfig;

    WebClientConfig(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @Bean
    public WebClient initWebClient() {
        log.info("initializing custom WebClient");

        try {

            SslContext sslContext = SslContextBuilder
                    .forClient()
                    .trustManager(InsecureTrustManagerFactory.INSTANCE)
                    .build();

            HttpClient httpClient = HttpClient.create().secure(sslContextSpec -> sslContextSpec.sslContext(sslContext));
            return WebClient.builder().baseUrl(appConfig.getBaseBackendUrl()).clientConnector(new ReactorClientHttpConnector(httpClient)).build();

        } catch (SSLException e) {
            log.error("Unable to create custom WebClient, providing default WebClient instead");
            return WebClient.builder().baseUrl(appConfig.getBaseBackendUrl()).build();
        }

    }

}
