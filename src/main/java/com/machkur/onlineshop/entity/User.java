package com.machkur.onlineshop.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class User {
    private int id;
    private String login;
    private String password;
    private LocalDate creationDate;
}
