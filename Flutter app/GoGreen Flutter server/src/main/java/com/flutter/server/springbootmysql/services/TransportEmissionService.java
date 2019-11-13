package com.flutter.server.springbootmysql.services;

import com.flutter.server.springbootmysql.entity.TransportEmission;
import com.flutter.server.springbootmysql.repos.TransportEmissionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransportEmissionService {

    private TransportEmissionRepo transportEmissionRepo;

    @Autowired
    public TransportEmissionService(TransportEmissionRepo transportEmissionRepo){
        this.transportEmissionRepo = transportEmissionRepo;
    }

    public TransportEmission findById(String vehicle){
        return this.transportEmissionRepo.findById(vehicle).orElse(null);
    }
}
