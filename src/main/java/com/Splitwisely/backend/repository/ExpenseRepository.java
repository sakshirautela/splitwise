package com.Splitwisely.backend.repository;

import com.Splitwisely.backend.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, String> {
}
