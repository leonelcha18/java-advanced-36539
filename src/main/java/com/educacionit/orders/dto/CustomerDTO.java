package com.educacionit.orders.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    private Long id = Long.valueOf(0);
    private String name;
    private String lastName;
    private int dni;
    private String mobile;
    private int age;
    private String country;
    private String email;
    private String raw;

    @Override
    public String toString () {
        return raw.concat(",").concat(this.id.toString());
    }
}