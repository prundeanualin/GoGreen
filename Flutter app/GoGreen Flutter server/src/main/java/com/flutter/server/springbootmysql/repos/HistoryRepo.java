package com.flutter.server.springbootmysql.repos;

import com.flutter.server.springbootmysql.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepo extends JpaRepository<History, String> {
}
