package com.example.restourant.service;

import org.springframework.stereotype.Service;

@Service
public class Test {
    public String hello(String message) {
        return "hello world: " + message;
    }
}
