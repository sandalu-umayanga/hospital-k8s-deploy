package com.backend.controller;

import com.backend.dto.NotificationDto;
import com.backend.dto.ResponseDto;
import com.backend.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/v1/notification")
@CrossOrigin
public class NotificationController {

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private ResponseDto responseDto;

    @GetMapping("/{userId}/{role}")
    public ResponseEntity<ResponseDto> getNotifications(@PathVariable int userId, @PathVariable String role) {
        try {
            List<NotificationDto> list = notificationService.getNotifications(userId, role);
            responseDto.setCode("05");
            responseDto.setMessage("Success");
            responseDto.setData(list);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (Exception e) {
            responseDto.setCode("000");
            responseDto.setMessage("Error");
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/markAsRead/{id}")
    public ResponseEntity<ResponseDto> markAsRead(@PathVariable int id) {
        try {
            notificationService.markAsRead(id);
            responseDto.setCode("06");
            responseDto.setMessage("Marked as read");
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (Exception e) {
            responseDto.setCode("000");
            responseDto.setMessage("Error");
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
