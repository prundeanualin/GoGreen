package com.flutter.server.springbootmysql.controllers;

import com.flutter.server.springbootmysql.entity.UserCareer;
import com.flutter.server.springbootmysql.services.UserCareerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserCareerController {

    private UserCareerService userCareerService;

    @Autowired
    public UserCareerController(UserCareerService userCareerService){
        this.userCareerService = userCareerService;
    }

    @GetMapping(value = "/usercareer/{userEmail}")
    public ResponseEntity<List<UserCareer>> findUserCareerById (@PathVariable("userEmail") String userEmail){
        List<UserCareer> list = this.userCareerService.findAllById(userEmail);
        return new ResponseEntity<List<UserCareer>>(list, HttpStatus.OK);
    }

    @GetMapping(value = "/createUserCareer/{userEmail}")
    public ResponseEntity<UserCareer> createUserCareer (@PathVariable("userEmail") String userEmail){
        UserCareer uc = new UserCareer(userEmail,0,0,0,0,0,0,0,0,0,0,0);
        userCareerService.createCareer(uc);
        return new ResponseEntity<UserCareer>(uc, HttpStatus.OK);
    }
}
