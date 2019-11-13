package com.flutter.server.springbootmysql.controllers;

import com.flutter.server.springbootmysql.entity.History;
import com.flutter.server.springbootmysql.services.HistoryService;
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
public class HistoryController {

    private HistoryService historyService;

    @Autowired
    public HistoryController(HistoryService historyService){
        this.historyService = historyService;
    }

    @GetMapping("/history/{userEmail}")
    public ResponseEntity<List<History>> findHistoryById(@PathVariable("userEmail") String userEmail){
        List<History> list = this.historyService.findAllById(userEmail);
        return new ResponseEntity<List<History>>(list, HttpStatus.OK);
    }
}
