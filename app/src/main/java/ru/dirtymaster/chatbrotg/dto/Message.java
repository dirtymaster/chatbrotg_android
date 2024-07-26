package ru.dirtymaster.chatbrotg.dto;

import java.time.Instant;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Message {
    private UUID id;
    private User author;
    private String content;
    private String createdAt;
}
