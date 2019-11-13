package com.flutter.server.springbootmysql.repos;

import com.flutter.server.springbootmysql.entity.FoodEmission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodEmissionRepo extends JpaRepository<FoodEmission, String> {
}
