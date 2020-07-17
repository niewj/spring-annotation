package com.niewj.service;

import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class ProductService {
    private String label="1";
    public void print(){
        System.out.println("ProductService --> print");
    }
}
