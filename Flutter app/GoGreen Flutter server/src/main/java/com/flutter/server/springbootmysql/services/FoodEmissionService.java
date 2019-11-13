package com.flutter.server.springbootmysql.services;

import com.flutter.server.springbootmysql.entity.FoodEmission;
import com.flutter.server.springbootmysql.repos.FoodEmissionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FoodEmissionService {

    private FoodEmissionRepo foodEmissionRepo;

    @Autowired
    public FoodEmissionService(FoodEmissionRepo foodEmissionRepo){
        this.foodEmissionRepo = foodEmissionRepo;
    }

    public Optional<FoodEmission> findById(String foodName){
        return foodEmissionRepo.findById(foodName);
    }

}
