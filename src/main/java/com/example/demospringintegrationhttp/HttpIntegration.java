package com.example.demospringintegrationhttp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.http.dsl.Http;

@Configuration
public class HttpIntegration {

    @Bean
    public IntegrationFlow multipartForm() {
        return IntegrationFlows.from(Http.inboundChannelAdapter("/documents")
                .statusCodeExpression(HttpStatus.NO_CONTENT.toString())
                .requestMapping(m -> m
                        .methods(HttpMethod.POST)
                        .consumes(MediaType.MULTIPART_FORM_DATA_VALUE)
                ))
                .handle(new MultipartReceiver())
                .get();
    }
}
