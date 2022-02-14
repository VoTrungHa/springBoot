package com.springboot.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
//@ComponentScan({"com.springboot.springboot"})
//@ImportResource("classpath:/spring/spring-config.xml")
public class SpringbootApplication {

    public static void main(String[] args) {
      SpringApplication.run(SpringbootApplication.class, args);


    }

}
