package com.flutter.server.springbootmysql.repos;

import com.flutter.server.springbootmysql.entity.TransportEmission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransportEmissionRepo extends JpaRepository<TransportEmission, String> {
}
