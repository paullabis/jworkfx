package com.jworkfx.app1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {
        "com.jworkfx.app1",
        "com.jworkfx.workflow"
})
public class App1 {

    public static void main(String[] args) {
        SpringApplication.run(App1.class, args);
    }

}
