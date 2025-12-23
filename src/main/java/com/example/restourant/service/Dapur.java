package com.example.restourant.service;

import org.springframework.stereotype.Service;

@Service // Memberi tahu spring ini object yang perlu dikelola
public class Dapur {
    public String masak(String menu) {
        return "chef sedang memasak: " + menu;
    }

}
