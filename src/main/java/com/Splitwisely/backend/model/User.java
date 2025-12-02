package com.Splitwisely.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String username;
    private String email;
    private String password;
    @ManyToMany(mappedBy = "members")
    private List<Group> groups = new ArrayList<>();
    Object verificationToken;
    boolean verified = false;
    double balance = 0;

    public Object getVerficationToken() {
        return verificationToken;
    }

    public void setVerficationToken(Object o) {
        this.verificationToken = o;
    }
}
