package com.machkur.onlineshop.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class User {
    private int id;
    private String email;
    private String password;
    private String salt;
    private String role;
    private LocalDate creationDate;
}
