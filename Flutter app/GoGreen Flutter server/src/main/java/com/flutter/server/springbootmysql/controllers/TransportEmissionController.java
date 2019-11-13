package com.flutter.server.springbootmysql.controllers;


import com.flutter.server.springbootmysql.entity.History;
import com.flutter.server.springbootmysql.entity.TransportEmission;
import com.flutter.server.springbootmysql.entity.UserCareer;
import com.flutter.server.springbootmysql.services.HistoryService;
import com.flutter.server.springbootmysql.services.TransportEmissionService;
import com.flutter.server.springbootmysql.services.UserCareerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

class transportResponse {
    float resultCo2;
    float resultMoney;

    public transportResponse(){

    }

    public transportResponse(float resultCo2, float resultMoney) {
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
        return "transportResponse{" +
            "resultCo2=" + resultCo2 +
            ", resultMoney=" + resultMoney +
            '}';
    }
}

@RestController
@RequestMapping("/api")
public class TransportEmissionController {

    private TransportEmissionService transportEmissionService;
    private UserCareerService userCareerService;
    private HistoryService historyService;

    @Autowired
    public TransportEmissionController(TransportEmissionService transportEmissionService, UserCareerService userCareerService, HistoryService historyService) {
        this.transportEmissionService = transportEmissionService;
        this.userCareerService = userCareerService;
        this.historyService = historyService;
    }

    @GetMapping(value = "/transport/{userEmail}/{vehicle1}/{vehicle2}/{distance}")
    public ResponseEntity<transportResponse> calcEmission(@PathVariable("userEmail") String userEmail,
                                                          @PathVariable("vehicle1") String vehicle1,
                                                          @PathVariable("vehicle2") String vehicle2,
                                                          @PathVariable("distance") float distance) {
        TransportEmission transportEmission1 = this.transportEmissionService.findById(vehicle1);
        TransportEmission transportEmission2 = this.transportEmissionService.findById(vehicle2);

        float netCo2Saved = transportEmission1.getCo2Km() * distance;
        float netMoneySaved = transportEmission2.getPriceKm() * distance;

        // Updating the current userCareer
        UserCareer uc = this.userCareerService.findById(userEmail);
        userCareerService.deleteCareer(userEmail);
        float totalco2Saved = uc.getTotalCo2Saved();
        float totalMoneySaved = uc.getTotalMoneySaved();
        float transportCo2Saved = uc.getTransportCo2Saved();
        float transportMoneySaved = uc.getTransportMoneySaved();

        totalco2Saved = totalco2Saved + netCo2Saved;
        totalMoneySaved = totalMoneySaved + netMoneySaved;
        transportCo2Saved = transportCo2Saved + netCo2Saved;
        transportMoneySaved = transportMoneySaved + netMoneySaved;

        uc.setTotalCo2Saved(totalco2Saved);
        uc.setTotalMoneySaved(totalMoneySaved);
        uc.setTransportCo2Saved(transportCo2Saved);
        uc.setTransportMoneySaved(transportMoneySaved);
        this.userCareerService.createCareer(uc);

        // Entering into History table
        LocalDate date = LocalDate.now();
        History history = new History(userEmail, "Transport", netCo2Saved, netMoneySaved, date);
        this.historyService.createHisory(history);

        // sending historyResponse POJO
        transportResponse tr = new transportResponse(netCo2Saved, netMoneySaved);
        return new ResponseEntity<transportResponse>(tr, HttpStatus.OK);


    }

}
