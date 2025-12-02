package com.Splitwisely.backend.controller;

import com.Splitwisely.backend.dto.BalanceUpdateDTO;
import com.Splitwisely.backend.dto.GroupRequestDTO;
import com.Splitwisely.backend.dto.GroupResponseDTO;
import com.Splitwisely.backend.service.GroupService;
import lombok.RequiredArgsConstructor;
import com.Splitwise.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;
    private final UserService userService; // Use the correct class name

    // ------------------------ GET ALL GROUPS FOR USER ------------------------
    // Mapped to GET /api/groups/user/{userId} for clarity
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<GroupResponseDTO>> getAllUserGroups(@PathVariable String userId) {
        return ResponseEntity.ok(groupService.getAllUserGroups(userId));
    }

    // ------------------------ CREATE GROUP ------------------------
    @PostMapping("/create/{userId}")
    public ResponseEntity<String> createGroup(@RequestBody GroupRequestDTO dto, @PathVariable String userId) {
        String groupId = groupService.createNewGroup(dto, userId);
        return ResponseEntity.ok(groupId);
    }

    // ------------------------ DELETE GROUP ------------------------
    @DeleteMapping("/{groupId}/{userId}")
    public ResponseEntity<Boolean> deleteGroup(@PathVariable String groupId, @PathVariable String userId) {
        Boolean deleted = groupService.deleteGroupPerm(groupId, userId);
        return ResponseEntity.ok(deleted);
    }

    // ------------------------ ADD USER TO GROUP ------------------------
    @PostMapping("/{groupId}/users/{userId}")
    public ResponseEntity<String> addUserToGroup(
            @PathVariable String groupId,
            @PathVariable String userId
    ) {
        String response = groupService.addUserToGroup(groupId, userId);
        return ResponseEntity.ok(response);
    }

    // ------------------------ REMOVE USER FROM GROUP ------------------------
    @DeleteMapping("/{groupId}/users/{userId}")
    public ResponseEntity<String> removeUserFromGroup(
            @PathVariable String groupId,
            @PathVariable String userId
    ) {
        String response = groupService.removeUserFromGroup(groupId, userId);
        return ResponseEntity.ok(response);
    }

    // ------------------------ UPDATE BALANCES ------------------------
    @PostMapping("/{groupId}/balances")
    public ResponseEntity<Boolean> groupSettlement(
            @PathVariable String groupId,
            @RequestBody List<BalanceUpdateDTO> updates
    ) {
        boolean success = groupService.groupSettlement(groupId, updates);
        return success ? ResponseEntity.ok(true) : ResponseEntity.badRequest().body(false);
    }
}
