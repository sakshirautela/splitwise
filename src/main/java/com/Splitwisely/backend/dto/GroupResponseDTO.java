package com.Splitwisely.backend.dto;

import com.Splitwisely.backend.model.Expense;
import com.Splitwisely.backend.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class GroupResponseDTO {
    private Long id;
    List<User> li;
    private String name;
    List<Expense> exp;
}
