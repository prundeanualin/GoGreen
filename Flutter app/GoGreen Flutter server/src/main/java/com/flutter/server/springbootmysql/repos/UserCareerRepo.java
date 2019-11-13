package com.flutter.server.springbootmysql.repos;

import com.flutter.server.springbootmysql.entity.UserCareer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCareerRepo extends JpaRepository<UserCareer,String> {
}
