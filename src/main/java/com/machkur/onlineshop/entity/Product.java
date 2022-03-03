package com.machkur.onlineshop.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Product {
    private int id;
    private String name;
    private double price;
    private LocalDate creationDate;
}
