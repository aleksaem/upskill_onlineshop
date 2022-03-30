package com.machkur.onlineshop.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private int id;
    private String name;
    private double price;
    private LocalDate creationDate;
}
