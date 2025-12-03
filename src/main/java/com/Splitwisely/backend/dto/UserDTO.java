package com.Splitwisely.backend.dto;

import com.Splitwisely.backend.model.Group;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class UserDTO {

    @Data
    public static class UserResponseDTO {
        private String id;
        private String name;
        private String email;
        private List<Group> groups = new ArrayList<>();
        private double balance;
    }

    @Data
    public static class CreateUserRequest {
        private String name;
        private String email;
        private String password;
    }

    @Data
    public static class UserRequestDTO {
        private String name;
        private String email;
        private String password;
    }
}
