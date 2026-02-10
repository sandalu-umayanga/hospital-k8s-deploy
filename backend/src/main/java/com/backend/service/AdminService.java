package com.backend.service;

import com.backend.dto.LoginDto;
import com.backend.entity.Admin;
import com.backend.repo.AdminRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AdminService {

    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String loginAdmin(LoginDto loginDto) {
        Admin admin = adminRepo.findByEmail(loginDto.getEmail());
        if (admin != null) {
            if (passwordEncoder.matches(loginDto.getPassword(), admin.getPassword())) {
                return "10";
            } else {
                return "11";
            }
        }
        return "03";
    }

    public Admin findByEmail(String email) {
        return adminRepo.findByEmail(email);
    }

    public void createAdmin(Admin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        adminRepo.save(admin);
    }
}
