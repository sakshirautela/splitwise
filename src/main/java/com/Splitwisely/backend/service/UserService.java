package com.Splitwise.backend.service;

import com.Splitwisely.backend.dto.BalanceUpdateDTO; // Still here, but unused in provided methods
import com.Splitwisely.backend.dto.UserRequestDTO;
import com.Splitwisely.backend.dto.UserResponseDTO;
import com.Splitwisely.backend.model.User;
import com.Splitwisely.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserResponseDTO getUser(String id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.map(user -> modelMapper.map(user, UserResponseDTO.class))
                .orElse(null);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    public Boolean editUser(String id, UserRequestDTO userDetails) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setUsername(userDetails.getUsername());
            user.setEmail(userDetails.getEmail());
            userRepository.save(user);
            return true;
        }
        return false;
    }

}
