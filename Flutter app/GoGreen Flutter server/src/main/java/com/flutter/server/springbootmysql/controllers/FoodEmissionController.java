package com.flutter.server.springbootmysql.controllers;

import com.flutter.server.springbootmysql.entity.FoodEmission;
import com.flutter.server.springbootmysql.entity.History;
import com.flutter.server.springbootmysql.entity.UserCareer;
import com.flutter.server.springbootmysql.services.FoodEmissionService;
import com.flutter.server.springbootmysql.services.HistoryService;
import com.flutter.server.springbootmysql.services.UserCareerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Optional;

class foodResponse {
    float resultCo2;
    float resultMoney;

    public foodResponse(){

    }

    public foodResponse(float resultCo2, float resultMoney) {
        this.resultCo2 = resultCo2;
        this.resultMoney = resultMoney;
    }

    public float getResultCo2() {
        return resultCo2;
    }

    public void setResultCo2(float resultCo2) {
        this.resultCo2 = resultCo2;
    }

    public float getResultMoney() {
        return resultMoney;
    }

    public void setResultMoney(float resultMoney) {
        this.resultMoney = resultMoney;
    }

    @Override
    public String toString() {
        return "foodResponse{" +
            "resultCo2=" + resultCo2 +
            ", resultMoney=" + resultMoney +
            '}';
    }
}

@RestController
@RequestMapping("/api")
public class FoodEmissionController {

    private FoodEmissionService foodEmissionService;
    private UserCareerService userCareerService;
    private HistoryService historyService;

    @Autowired
    public FoodEmissionController(FoodEmissionService foodEmissionService, UserCareerService userCareerService, HistoryService historyService) {
        this.foodEmissionService = foodEmissionService;
        this.userCareerService = userCareerService;
        this.historyService = historyService;
    }

    @GetMapping(value = "/{userEmail}/{food1}/{price1}/{food2}/{price2}")
    public ResponseEntity<foodResponse> calcEmission(@PathVariable("userEmail") String userEmail,
                                               @PathVariable("food1") String food1,
                                               @PathVariable("price1") float price1,
                                               @PathVariable("food2") String food2,
                                               @PathVariable("price2") float price2) {
        Optional<FoodEmission> food1Emission = this.foodEmissionService.findById(food1);
        Optional<FoodEmission> food2Emission = this.foodEmissionService.findById(food2);

        float food1Co2 = food1Emission.get().getCo2Produced();
        float food2Co2 = food2Emission.get().getCo2Produced();

        float netFoodCo2 = food1Co2 - food2Co2;
        float netMoneySaved = price1 - price2;

        // Updating the current userCareer entry
        UserCareer uc = this.userCareerService.findById(userEmail);
        userCareerService.deleteCareer(userEmail);
        float totalCo2Saved = uc.getTotalCo2Saved();
        float totalMoneySaved = uc.getTotalMoneySaved();
        float foodCo2Saved = uc.getFoodCo2Saved();
        float foodMoneySaved = uc.getFoodMoneySaved();

        totalCo2Saved = totalCo2Saved + netFoodCo2;
        totalMoneySaved = totalMoneySaved + netMoneySaved;
        foodCo2Saved = foodCo2Saved + netFoodCo2;
        foodMoneySaved = foodMoneySaved + netMoneySaved;

        uc.setTotalCo2Saved(totalCo2Saved);
        uc.setTotalMoneySaved(totalMoneySaved);
        uc.setFoodCo2Saved(foodCo2Saved);
        uc.setFoodMoneySaved(foodMoneySaved);
        userCareerService.createCareer(uc);

        // Entering into History table
        LocalDate date = LocalDate.now();
        History history = new History(userEmail, "VeggieMeal", netFoodCo2, netMoneySaved, date);
        this.historyService.createHisory(history);

        // sending foodResponse POJO back
        foodResponse fr = new foodResponse(netFoodCo2, netMoneySaved);
        return new ResponseEntity<foodResponse>(fr, HttpStatus.OK);


    }
}
