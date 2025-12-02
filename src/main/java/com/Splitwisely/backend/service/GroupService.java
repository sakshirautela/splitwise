package com.Splitwisely.backend.service;

import com.Splitwisely.backend.dto.BalanceUpdateDTO;
import com.Splitwisely.backend.dto.GroupRequestDTO;
import com.Splitwisely.backend.dto.GroupResponseDTO;
import com.Splitwisely.backend.model.Group;
import com.Splitwisely.backend.model.User;
import com.Splitwisely.backend.repository.GroupRepository;
import com.Splitwisely.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.ls.LSException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    public List<GroupResponseDTO> getAllUserGroups(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        List<Group> groups = user.getGroups();

        return groups.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public String createNewGroup(GroupRequestDTO dto, String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        Group group = new Group();
        group.setName(dto.getName());

        group.getMembers().add(user);
        user.getGroups().add(group);

        Group savedGroup = groupRepository.save(group);
        userRepository.save(user);

        return String.valueOf(savedGroup.getId());
    }

    @Transactional
    public Boolean deleteGroupPerm(String groupId, String userId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found with ID: " + groupId));

        for (User member : group.getMembers()) {
            member.getGroups().remove(group);
            userRepository.save(member);
        }

        groupRepository.delete(group);
        return true;
    }

    @Transactional
    public String removeUserFromGroup(String groupId, String userId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found with ID: " + groupId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        if (group.getMembers().remove(user) && user.getGroups().remove(group)) {
            userRepository.save(user);
            groupRepository.save(group);
            return "User removed from group successfully.";
        } else {
            return "User was not a member of this group.";
        }
    }

    @Transactional
    public String addUserToGroup(String groupId, String userId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found with ID: " + groupId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        if (!group.getMembers().contains(user)) {
            group.getMembers().add(user);
            user.getGroups().add(group);

            userRepository.save(user);
            groupRepository.save(group);
            return "User added to group successfully.";
        } else {
            return "User is already in the group.";
        }
    }

    public boolean groupSettlement(String groupId, List<BalanceUpdateDTO> updates) {
        return true;
    }

    private GroupResponseDTO convertToDTO(Group group) {
        return modelMapper.map(group, GroupResponseDTO.class);
    }
}
