package com.Splitwisely.backend.model;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @ManyToMany(mappedBy = "members")
    @JsonIgnore   // prevents infinite recursion when returning JSON
    private List<Group> groups = new ArrayList<>();

    private String verificationToken;

    private boolean verified = false;

    private double balance = 0.0;

    public void setVerficationToken(String token) {
        this.verificationToken = token;
    }

    public String getVerficationToken() {
        return this.verificationToken;
    }
}
