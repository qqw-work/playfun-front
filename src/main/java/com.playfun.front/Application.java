package com.playfun.front;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import xyz.erupt.core.annotation.EruptScan;

/*
todo:
    1.提现记录 - done
    2.充值记录 - done
    3.购买记录 - done
    4.每日收益记录 - done
    5.商品管理 - done

    6.版本强制更新
    7.手动提现
*/

@SpringBootApplication
@EntityScan
@EruptScan
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
