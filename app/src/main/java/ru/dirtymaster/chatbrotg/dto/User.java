package ru.dirtymaster.chatbrotg.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private UUID id;
    private String name;
    private String email;
    private String passwordHash;
}
