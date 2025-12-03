package com.Splitwisely.backend.service;

import com.Splitwisely.backend.dto.GroupDTO.*;
import com.Splitwisely.backend.model.*;
import com.Splitwisely.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepo;
    private final UserRepository userRepo;
    private final ExpenseRepository expenseRepo;

    public GroupResponseDTO createGroup(CreateGroupRequest request) {
        Group group = new Group();
        group.setName(request.getName());
        List<User> members = userRepo.findAllById(request.getMemberIds());
        group.setMembers(members);

        members.forEach(m -> m.getGroups().add(group));

        groupRepo.save(group);

        return toDTO(group);
    }

    public GroupResponseDTO getGroupById(String groupId) {
        Group group = groupRepo.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));
        return toDTO(group);
    }

    public List<GroupResponseDTO> getGroupsOfUser(String userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Group> groups = user.getGroups();

        List<GroupResponseDTO> list = new ArrayList<>();
        groups.forEach(g -> list.add(toDTO(g)));
        return list;
    }

    public GroupResponseDTO editGroup(String groupId, EditGroupRequest request) {
        Group group = groupRepo.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        group.setName(request.getName());
        groupRepo.save(group);

        return toDTO(group);
    }

    public boolean deleteGroup(String groupId) {
        Group group = groupRepo.getReferenceById(groupId);
        if (group.getTotalExpenses() == 0) {
            groupRepo.deleteById(groupId);
            return true;
        } else {
            return false;
        }
    }

    public GroupResponseDTO addMember(String groupId, String userId) {
        Group group = groupRepo.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!group.getMembers().contains(user)) {
            group.getMembers().add(user);
            user.getGroups().add(group);
        }

        groupRepo.save(group);

        return toDTO(group);
    }

    public GroupResponseDTO removeMember(String groupId, String userId) {
        Group group = groupRepo.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        group.getMembers().remove(user);
        user.getGroups().remove(group);

        groupRepo.save(group);

        return toDTO(group);
    }

    public GroupResponseDTO addExpense(String groupId, AddExpenseRequest request) {

        Group group = groupRepo.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        Expense expense = new Expense();
        expense.setDescription(request.getDescription());
        expense.setTotalExpenses(request.getAmount());

        User paidBy = userRepo.findById(request.getPaidByUserId())
                .orElseThrow(() -> new RuntimeException("PaidBy user not found"));

        List<User> splitUsers = userRepo.findAllById(request.getSplitBetweenUserIds());

        expense.setPaidBy(paidBy.getId());
        expense.setSplitBetween(splitUsers);
        expense.setGroup(group);

        group.getExpenses().add(expense);
        group.setTotalExpenses(group.getTotalExpenses() + request.getAmount());

        expenseRepo.save(expense);
        groupRepo.save(group);

        return toDTO(group);
    }

    public Map<String, Double> calculateBalances(String groupId) {
        Group group = groupRepo.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        Map<String, Double> balanceMap = new HashMap<>();

        // Initialize
        for (User u : group.getMembers()) {
            balanceMap.put(u.getName(), 0.0);
        }

        // Calculate
        for (Expense e : group.getExpenses()) {
            double share = e.getTotalExpenses() / e.getSplitBetween().size();

            for (User u : e.getSplitBetween()) {
                balanceMap.put(u.getName(), balanceMap.get(u.getName()) - share);
            }
            User u = userRepo.getReferenceById(e.getPaidBy());
            balanceMap.put(u.getName(),
                    balanceMap.get(u.getName()) + e.getTotalExpenses());
        }

        return balanceMap;
    }

    private GroupResponseDTO toDTO(Group group) {
        GroupResponseDTO dto = new GroupResponseDTO();

        dto.setId(group.getId());
        dto.setName(group.getName());
        dto.setTotalExpenses(group.getTotalExpenses());
        List<User> memberNames = new ArrayList<>();
        group.getMembers().forEach(m -> memberNames.add(m));
        dto.setMembers(memberNames);
        return dto;
    }
}
