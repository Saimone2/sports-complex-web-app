package com.saimone.SportsComplexApplication.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Поле обов'язкове для заповнення!")
    @Size(min = 2, max = 30, message = "Данні введено некоректно!")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotBlank(message = "Поле обов'язкове для заповнення!")
    @Size(min = 2, max = 30, message = "Данні введено некоректно!")
    @Column(name = "name", nullable = false)
    private String firstName;

    @Column(name = "address")
    private String address;

    @NotBlank(message = "Поле обов'язкове для заповнення!")
    @Email(message = "Некоректний e-mail!")
    @Column(name = "email", nullable = false)
    private String email;
}
