package ru.mail.romanov1234567890987.springbootmodule.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"ru.mail.romanov1234567890987.service",
        "ru.mail.romanov1234567890987.repository"})
public class AppConfig {
}