package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Value;

@RestController
@RequestMapping("/info")
public class InfoController {

    @Value("${server.port}")
    private String port;

    @GetMapping("/port")
    public String getPort() {
        return "Текущий порт приложения: " + port;
    }
}