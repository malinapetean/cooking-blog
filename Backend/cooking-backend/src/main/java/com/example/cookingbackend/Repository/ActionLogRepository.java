package com.example.cookingbackend.Repository;

import com.example.cookingbackend.Model.ActionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ActionLogRepository extends JpaRepository<ActionLog, Long> {

    @Query("SELECT al.user.id, COUNT(al) FROM ActionLog al WHERE al.timestamp > :since GROUP BY al.user.id")
    List<Object[]> countActionsByUserSince(@Param("since") LocalDateTime since);
}