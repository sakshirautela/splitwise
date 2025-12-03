package com.Splitwisely.backend.service;

import com.Splitwisely.backend.model.User;
import com.Splitwisely.backend.repository.UserRepository;
import org.jspecify.annotations.Nullable;
import com.Splitwisely.backend.dto.UserDTO.UserResponseDTO;
import com.Splitwisely.backend.dto.UserDTO.UserRequestDTO;
import com.Splitwisely.backend.dto.UserDTO.CreateUserRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public UserResponseDTO getUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return modelMapper.map(user, UserResponseDTO.class);
    }

    public UserResponseDTO createUser(CreateUserRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());

        User saved = userRepository.save(user);
        return modelMapper.map(saved, UserResponseDTO.class);
    }

    public Boolean isExist(String mail) {
        return userRepository.findByEmail(mail) != null;
    }

    public Boolean deleteUser(String id) {
        if (!userRepository.existsById(id)) return false;

        userRepository.deleteById(id);
        return true;
    }

    public Boolean editUser(String id, UserRequestDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());

        userRepository.save(user);
        return true;
    }
}
