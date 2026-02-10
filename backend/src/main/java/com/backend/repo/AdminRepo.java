package com.backend.repo;

import com.backend.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdminRepo extends JpaRepository<Admin, Integer> {
    @Query(value = "SELECT * FROM admin WHERE email = ?1", nativeQuery = true)
    Admin findByEmail(String email);
}
