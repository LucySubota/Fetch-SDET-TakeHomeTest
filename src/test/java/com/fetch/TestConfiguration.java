package com.fetch;

import com.fetch.test.GeoCodingTest;
import com.fetch.utils.Helper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.fetch.utils")
public class TestConfiguration {

    @Bean
    public Helper helper() { return new Helper(); }

    @Bean
    public GeoCodingTest geoCodingTest() { return new GeoCodingTest(); }

}
