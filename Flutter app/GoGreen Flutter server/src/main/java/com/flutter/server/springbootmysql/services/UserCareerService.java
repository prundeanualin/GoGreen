package com.flutter.server.springbootmysql.services;

import com.flutter.server.springbootmysql.entity.UserCareer;
import com.flutter.server.springbootmysql.repos.UserCareerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserCareerService {

    private UserCareerRepo userCareerRepo;

    // yet have to implement the update method

    @Autowired
    private UserCareerService(UserCareerRepo userCareerRepo){
        this.userCareerRepo = userCareerRepo;
    }

    public List<UserCareer> findAll(){
        return this.userCareerRepo.findAll();
    }

    public UserCareer findById(String userEmail){
        return this.userCareerRepo.findById(userEmail).orElse(null);
    }

    public void createCareer(UserCareer uc){
        this.userCareerRepo.save(uc);
    }

    public void deleteCareer(String userEmail){
        this.userCareerRepo.deleteById(userEmail);
    }

    public List<UserCareer> findAllById(String userEmail){
        List<UserCareer> list = findAll().stream()
            .filter(history -> history.getUserEmail().equals(userEmail))
            .collect(Collectors.toList());
        return list;
    }

}
