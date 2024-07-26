package ru.dirtymaster.chatbrotg.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewMessageRequest {
    private UUID authorId;
    private String content;
}
