package com.Splitwisely.backend.controller;

import com.Splitwisely.backend.dto.GroupDTO.*;
import com.Splitwisely.backend.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    // CREATE GROUP
    @PostMapping
    public ResponseEntity<GroupResponseDTO> createGroup(@RequestBody CreateGroupRequest request) {
        return ResponseEntity.ok(groupService.createGroup(request));
    }

    // GET GROUP BY ID
    @GetMapping("/{groupId}")
    public ResponseEntity<GroupResponseDTO> getGroupById(@PathVariable String groupId) {
        return ResponseEntity.ok(groupService.getGroupById(groupId));
    }

    // GET ALL GROUPS OF A USER
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getGroupsOfUser(@PathVariable String userId) {
        return ResponseEntity.ok(groupService.getGroupsOfUser(userId));
    }

    // EDIT GROUP NAME
    @PutMapping("/{groupId}")
    public ResponseEntity<GroupResponseDTO> editGroup(
            @PathVariable String groupId,
            @RequestBody EditGroupRequest request
    ) {
        return ResponseEntity.ok(groupService.editGroup(groupId, request));
    }

    // DELETE GROUP
    @DeleteMapping("/{groupId}")
    public ResponseEntity<?> deleteGroup(@PathVariable String groupId) {
        boolean b=groupService.deleteGroup(groupId);
        if(b){
        return ResponseEntity.ok("Group Deleted Successfully");

        }else{
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    // ADD MEMBER
    @PostMapping("/{groupId}/members/{userId}")
    public ResponseEntity<GroupResponseDTO> addMember(
            @PathVariable String groupId,
            @PathVariable String userId
    ) {
        return ResponseEntity.ok(groupService.addMember(groupId, userId));
    }

    // REMOVE MEMBER
    @DeleteMapping("/{groupId}/members/{userId}")
    public ResponseEntity<GroupResponseDTO> removeMember(
            @PathVariable String groupId,
            @PathVariable String userId
    ) {
        return ResponseEntity.ok(groupService.removeMember(groupId, userId));
    }

    // (OPTIONAL) ADD EXPENSE
    @PostMapping("/{groupId}/expenses")
    public ResponseEntity<GroupResponseDTO> addExpense(
            @PathVariable String groupId,
            @RequestBody AddExpenseRequest request
    ) {
        return ResponseEntity.ok(groupService.addExpense(groupId, request));
    }

    // (OPTIONAL) GET GROUP BALANCES
    @GetMapping("/{groupId}/balances")
    public ResponseEntity<?> getGroupBalances(@PathVariable String groupId) {
        return ResponseEntity.ok(groupService.calculateBalances(groupId));
    }
}
