package com.example.testwx;

import com.example.testwx.service.CreateMenu;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestwxApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestwxApplication.class, args);
//        CreateMenu.create();
    }

}
