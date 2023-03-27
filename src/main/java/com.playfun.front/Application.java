package com.playfun.front;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import xyz.erupt.core.annotation.EruptScan;

/*
todo: 手动提现
*/

@SpringBootApplication
@EntityScan
@EruptScan
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
