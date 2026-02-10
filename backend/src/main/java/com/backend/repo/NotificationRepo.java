package com.backend.repo;

import com.backend.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface NotificationRepo extends JpaRepository<Notification, Integer> {
    @Query(value = "SELECT * FROM notification WHERE user_id = ?1 AND user_role = ?2 ORDER BY timestamp DESC", nativeQuery = true)
    List<Notification> findByUserIdAndRole(int userId, String userRole);
}
