package com.backend.config;

import com.backend.entity.Admin;
import com.backend.repo.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${ADMIN_EMAIL}")
    private String adminEmail;

    @Value("${ADMIN_PASSWORD}")
    private String adminPassword;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("DataSeeder running...");
        if (adminRepo.count() == 0) {
            Admin admin = new Admin();
            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setFirstName("System");
            admin.setLastName("Admin");
            adminRepo.save(admin);
            System.out.println("Default Admin created: " + adminEmail);
        }
    }
}
