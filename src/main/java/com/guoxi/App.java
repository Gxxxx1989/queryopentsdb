package com.guoxi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@ComponentScan("com.guoxi")
@EnableScheduling//开启定时任
public class App {
    public  static  void  main(String []args){
        SpringApplication.run(App.class, args);
    }
}
