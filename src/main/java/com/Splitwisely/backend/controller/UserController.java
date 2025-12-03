package com.Splitwisely.backend.controller;

import com.Splitwisely.backend.dto.UserDTO.UserResponseDTO;
import com.Splitwisely.backend.dto.UserDTO.UserRequestDTO;
import com.Splitwisely.backend.dto.UserDTO.CreateUserRequest;
import com.Splitwisely.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/id/{id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable String id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @GetMapping("/email/{mail}")
    public ResponseEntity<Boolean> isExist(@PathVariable String mail) {
        return ResponseEntity.ok(userService.isExist(mail));
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody CreateUserRequest request) {
        return ResponseEntity.ok(userService.createUser(request));
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable String id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<Boolean> editUser(@PathVariable String id, @RequestBody UserRequestDTO dto) {
        return ResponseEntity.ok(userService.editUser(id, dto));
    }
}
