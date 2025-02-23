package com.example.convenientstoresspringboot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@Slf4j
@ServletComponentScan
public class ConvenientStoresSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConvenientStoresSpringbootApplication.class, args);
        log.info("serve start");
    }
}
