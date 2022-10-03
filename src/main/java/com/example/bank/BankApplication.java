package com.example.bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringBootApplication
public class BankApplication {


    public static void main(String[] args) {
        SpringApplication.run(BankApplication.class, args);
    }

}
