package com.Splitwisely.backend.dto;

import com.Splitwisely.backend.model.Group;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserResponseDTO {
    @Id
    private String id;
    private String name;
    private String email;
    private List<Group> groups = new ArrayList<>();
    private double balance;

}
