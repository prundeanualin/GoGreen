package com.flutter.server.springbootmysql.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "transport_emission")
public class TransportEmission {

    // define Fields
    @Id
    @Column(name ="vehicle")
    private String vehicle;

    @Column(name = "co2_km")
    private float co2Km;

    @Column(name = "price_km")
    private float priceKm;

    public TransportEmission(){

    }

    public TransportEmission(String vehicle, float co2Km, float priceKm) {
        this.vehicle = vehicle;
        this.co2Km = co2Km;
        this.priceKm = priceKm;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public float getCo2Km() {
        return co2Km;
    }

    public void setCo2Km(float co2Km) {
        this.co2Km = co2Km;
    }

    public float getPriceKm() {
        return priceKm;
    }

    public void setPriceKm(float priceKm) {
        this.priceKm = priceKm;
    }

    @Override
    public String toString() {
        return "TransportEmission{" +
            "vehicle='" + vehicle + '\'' +
            ", co2Km=" + co2Km +
            ", priceKm=" + priceKm +
            '}';
    }
}
