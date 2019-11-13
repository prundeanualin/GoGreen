package com.flutter.server.springbootmysql.services;

import com.flutter.server.springbootmysql.entity.History;
import com.flutter.server.springbootmysql.repos.HistoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoryService {

    private HistoryRepo historyRepo;

    @Autowired
    public HistoryService(HistoryRepo historyRepo){
        this.historyRepo = historyRepo;
    }

    public void createHisory(History history){
        this.historyRepo.save(history);
    }

    public List<History> findAll(){
        return historyRepo.findAll();
    }

    public List<History> findAllById(String userEmail){
        List<History> list = findAll().stream()
            .filter(history -> history.getUserEmail().equals(userEmail))
            .collect(Collectors.toList());
        return list;
    }
}
