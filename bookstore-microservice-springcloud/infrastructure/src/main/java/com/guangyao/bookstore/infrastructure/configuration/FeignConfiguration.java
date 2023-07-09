package com.guangyao.bookstore.infrastructure.configuration;

import feign.Contract;
import feign.RequestInterceptor;
import feign.jaxrs2.JAXRS2Contract;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;

import javax.inject.Inject;

@Configuration
@Profile("!test")
@EnableFeignClients(basePackages = {"com.guangyao.bookstore"})
public class FeignConfiguration {

    @Inject
    private ClientCredentialsResourceDetails resource;

    @Bean
    public Contract feignContract() { return new JAXRS2Contract(); }

    @Bean
    public RequestInterceptor oauth2FeignRequestInterceptor() {
        return new OAuth2FeignRequestInterceptor(new DefaultOAuth2ClientContext(), resource);
    }
}
