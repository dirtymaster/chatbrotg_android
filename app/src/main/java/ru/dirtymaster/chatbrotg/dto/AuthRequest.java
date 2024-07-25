package ru.dirtymaster.chatbrotg.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthRequest {
    private String name;
    private String email;
    private String passwordHash;
}
