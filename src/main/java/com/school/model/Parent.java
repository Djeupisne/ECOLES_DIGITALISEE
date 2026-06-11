package com.school.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "parents")
public class Parent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String phone;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}