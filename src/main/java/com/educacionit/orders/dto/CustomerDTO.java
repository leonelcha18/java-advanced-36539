package com.educacionit.orders.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    private String name;
    private String lastName;
    private int dni;
    private String mobile;
    private int age;
    private String country;
    private String email;
}