package com.guangyao.bookstore.infrastructure.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackages = {"com.guangyao.bookstore"})
public class JPAConfiguration {
}
