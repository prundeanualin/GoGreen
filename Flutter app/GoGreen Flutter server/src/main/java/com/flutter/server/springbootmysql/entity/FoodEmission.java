package com.flutter.server.springbootmysql.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "food_emission")
public class FoodEmission {

    // Define fields
    @Id
    @Column(name = "food")
    private String food;

    @Column(name = "co2_produced")
    private float co2Produced;

    public FoodEmission(){
    }

    public FoodEmission(String food, float co2Produced) {
        this.food = food;
        this.co2Produced = co2Produced;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public float getCo2Produced() {
        return co2Produced;
    }

    public void setCo2Produced(float co2Produced) {
        this.co2Produced = co2Produced;
    }


    @Override
    public String toString() {
        return "FoodEmission{" +
            "food='" + food + '\'' +
            ", co2Produced='" + co2Produced + '\'' +
            '}';
    }
}
