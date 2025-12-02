package com.Splitwisely.backend.controller;

import com.Splitwisely.backend.dto.UserRequestDTO;
import com.Splitwisely.backend.dto.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final com.Splitwise.backend.service.UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable String id) {
        return ResponseEntity.ok(userService.getUser(id));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> editUser(
            @PathVariable String id,
            @RequestBody UserRequestDTO userDetails
    ) {
        return ResponseEntity.ok(userService.editUser(id, userDetails));
    }

}
