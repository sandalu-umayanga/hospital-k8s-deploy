package com.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NotificationDto {
    private int id;
    private int userId;
    private String userRole;
    private String message;
    private LocalDateTime timestamp;
    private boolean isRead;
}
