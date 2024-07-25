package ru.dirtymaster.chatbrotg;

import java.util.UUID;

import lombok.Data;

@Data
public class UserResponse {
    private UUID id;
    private String name;
    private String email;
    private String passwordHash;
}
