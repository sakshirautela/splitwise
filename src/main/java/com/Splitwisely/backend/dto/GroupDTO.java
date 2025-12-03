package com.Splitwisely.backend.dto;

import com.Splitwisely.backend.model.User;
import lombok.Data;
import java.util.List;

public class GroupDTO {

    @Data
    public static class CreateGroupRequest {
        private String name;
//        private String userid;
        private List<String> memberIds;
    }

    @Data
    public static class EditGroupRequest {
        private String name;
    }

    @Data
    public static class AddExpenseRequest {
        private String description;
        private double amount;
        private String paidByUserId;
        private List<String> splitBetweenUserIds;
    }

    @Data
    public static class GroupResponseDTO {
        private String id;
        private String name;
        private List<User> members;
        private double totalExpenses;
    }
}
