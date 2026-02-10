package com.backend.controller;

import com.backend.dto.LoginDto;
import com.backend.dto.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/admin")
@CrossOrigin
public class AdminController {

    @Autowired
    private com.backend.service.AdminService adminService;

    @Autowired
    private ResponseDto responseDto;

    @Autowired
    private com.backend.util.JwtUtils jwtUtils;

    @PostMapping("/loginAdmin")
    public ResponseEntity<ResponseDto> loginAdmin(@RequestBody LoginDto loginDto) {
        try {
            String res = adminService.loginAdmin(loginDto);
            if (res.equals("10")) {
                responseDto.setCode("10");
                responseDto.setMessage("Admin logged in successfully");
                responseDto.setData(adminService.findByEmail(loginDto.getEmail()));
                responseDto.setToken(jwtUtils.generateToken(loginDto.getEmail(), "ADMIN"));
                return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
            } else if (res.equals("11")) {
                responseDto.setCode("11");
                responseDto.setMessage("Invalid password");
                return new ResponseEntity<>(responseDto, HttpStatus.UNAUTHORIZED);
            } else {
                responseDto.setCode("03");
                responseDto.setMessage("Admin not found");
                return new ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            responseDto.setCode("000");
            responseDto.setMessage("Error occurred");
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
