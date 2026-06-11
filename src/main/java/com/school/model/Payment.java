package com.school.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    private BigDecimal amount;
    private String currency = "USD";
    private String status; // PENDING, COMPLETED, FAILED
    private String method; // MOBILE_MONEY, CARD
    private String transactionRef;

    @Column(updatable = false)
    private LocalDateTime paymentDate = LocalDateTime.now();
}