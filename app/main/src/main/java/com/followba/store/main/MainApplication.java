package com.followba.store.main;

import com.followba.store.auth.annotation.ImportAuth;
import com.followba.store.product.annotation.ImportProduct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@ImportAuth
@ImportProduct
@EnableScheduling
@SpringBootApplication
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
