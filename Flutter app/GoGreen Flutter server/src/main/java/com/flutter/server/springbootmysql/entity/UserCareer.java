package com.flutter.server.springbootmysql.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_career")
public class UserCareer {

    // define fields
    @Id
    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "total_co2_saved")
    private float totalCo2Saved;

    @Column(name = "total_money_saved")
    private float totalMoneySaved;

    @Column(name = "total_energy_saved")
    private float totalEnergySaved;

    @Column(name = "food_co2_saved")
    private float foodCo2Saved;

    @Column(name = "transport_co2_saved")
    private float transportCo2Saved;

    @Column(name = "solar_co2_saved")
    private float solarCo2Saved;

    @Column(name = "temp_co2_saved")
    private float tempCo2Saved;

    @Column(name = "food_money_saved")
    private float foodMoneySaved;

    @Column(name = "transport_money_saved")
    private float transportMoneySaved;

    @Column(name = "solar_money_saved")
    private float solarMoneySaved;

    @Column(name = "temp_money_saved")
    private float tempMoneySaved;

    public UserCareer() {

    }

    public UserCareer(String userEmail, float totalCo2Saved, float totalMoneySaved, float totalEnergySaved, float foodCo2Saved, float transportCo2Saved, float solarCo2Saved, float tempCo2Saved, float foodMoneySaved, float transportMoneySaved, float solarMoneySaved, float tempMoneySaved) {
        this.userEmail = userEmail;
        this.totalCo2Saved = totalCo2Saved;
        this.totalMoneySaved = totalMoneySaved;
        this.totalEnergySaved = totalEnergySaved;
        this.foodCo2Saved = foodCo2Saved;
        this.transportCo2Saved = transportCo2Saved;
        this.solarCo2Saved = solarCo2Saved;
        this.tempCo2Saved = tempCo2Saved;
        this.foodMoneySaved = foodMoneySaved;
        this.transportMoneySaved = transportMoneySaved;
        this.solarMoneySaved = solarMoneySaved;
        this.tempMoneySaved = tempMoneySaved;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public float getTotalCo2Saved() {
        return totalCo2Saved;
    }

    public void setTotalCo2Saved(float totalCo2Saved) {
        this.totalCo2Saved = totalCo2Saved;
    }

    public float getTotalMoneySaved() {
        return totalMoneySaved;
    }

    public void setTotalMoneySaved(float totalMoneySaved) {
        this.totalMoneySaved = totalMoneySaved;
    }

    public float getTotalEnergySaved() {
        return totalEnergySaved;
    }

    public void setTotalEnergySaved(float totalEnergySaved) {
        this.totalEnergySaved = totalEnergySaved;
    }

    public float getFoodCo2Saved() {
        return foodCo2Saved;
    }

    public void setFoodCo2Saved(float foodCo2Saved) {
        this.foodCo2Saved = foodCo2Saved;
    }

    public float getTransportCo2Saved() {
        return transportCo2Saved;
    }

    public void setTransportCo2Saved(float transportCo2Saved) {
        this.transportCo2Saved = transportCo2Saved;
    }

    public float getSolarCo2Saved() {
        return solarCo2Saved;
    }

    public void setSolarCo2Saved(float solarCo2Saved) {
        this.solarCo2Saved = solarCo2Saved;
    }

    public float getTempCo2Saved() {
        return tempCo2Saved;
    }

    public void setTempCo2Saved(float tempCo2Saved) {
        this.tempCo2Saved = tempCo2Saved;
    }

    public float getFoodMoneySaved() {
        return foodMoneySaved;
    }

    public void setFoodMoneySaved(float foodMoneySaved) {
        this.foodMoneySaved = foodMoneySaved;
    }

    public float getTransportMoneySaved() {
        return transportMoneySaved;
    }

    public void setTransportMoneySaved(float transportMoneySaved) {
        this.transportMoneySaved = transportMoneySaved;
    }

    public float getSolarMoneySaved() {
        return solarMoneySaved;
    }

    public void setSolarMoneySaved(float solarMoneySaved) {
        this.solarMoneySaved = solarMoneySaved;
    }

    public float getTempMoneySaved() {
        return tempMoneySaved;
    }

    public void setTempMoneySaved(float tempMoneySaved) {
        this.tempMoneySaved = tempMoneySaved;
    }

    @Override
    public String toString() {
        return "UserCareer{" +
            "userEmail='" + userEmail + '\'' +
            ", totalCo2Saved=" + totalCo2Saved +
            ", totalMoneySaved=" + totalMoneySaved +
            ", totalEnergySaved=" + totalEnergySaved +
            ", foodCo2Saved=" + foodCo2Saved +
            ", transportCo2Saved=" + transportCo2Saved +
            ", solarCo2Saved=" + solarCo2Saved +
            ", tempCo2Saved=" + tempCo2Saved +
            ", foodMoneySaved=" + foodMoneySaved +
            ", transportMoneySaved=" + transportMoneySaved +
            ", solarMoneySaved=" + solarMoneySaved +
            ", tempMoneySaved=" + tempMoneySaved +
            '}';
    }
}
