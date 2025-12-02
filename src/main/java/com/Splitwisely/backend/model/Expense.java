package com.Splitwisely.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String description;
    private double amount;

    private String paidBy; // userId

    @ElementCollection
    private List<Split> splitBetween = new ArrayList<>();

    @ManyToOne
    private Group group;
    @Column(columnDefinition = "DATETIME DEFAULT GETDATE()")
    private LocalDateTime createdAt;
}
