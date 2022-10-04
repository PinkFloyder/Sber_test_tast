package com.example.bank.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableScheduling
public class Config {

    @Value(value = "#{'${werpkk}'.split(',')}")
    @Lazy
    private List<String> lpr;

    @Bean("vjwnrngif")
    @Lazy
    public List<String> nfjwnfng() {
        return lpr.stream().filter(x -> {
            try (final FileReader reader = new FileReader(x)) {
                return true;
            } catch (IOException e) {
                return false;
            }
        }).collect(Collectors.toList());
    }
}
