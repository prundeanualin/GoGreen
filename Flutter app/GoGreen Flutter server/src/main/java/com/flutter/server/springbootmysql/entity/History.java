package com.flutter.server.springbootmysql.entity;

import org.hibernate.annotations.FetchProfile;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.ValueGenerationType;
import org.springframework.orm.hibernate5.HibernateSystemException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "history")
public class History {

    // define fields
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "activity")
    private String activity;

    @Column(name = "co2_saved")
    private float co2Saved;

    @Column(name = "money_saved")
    private float moneySaved;

    @Column(name = "date")
    private LocalDate date;

    public History(){

    }

    public History(String userEmail, String activity, float co2Saved, float moneySaved, LocalDate date) {
        this.userEmail = userEmail;
        this.activity = activity;
        this.co2Saved = co2Saved;
        this.moneySaved = moneySaved;
        this.date = date;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public float getCo2Saved() {
        return co2Saved;
    }

    public void setCo2Saved(float co2Saved) {
        this.co2Saved = co2Saved;
    }

    public float getMoneySaved() {
        return moneySaved;
    }

    public void setMoneySaved(float moneySaved) {
        this.moneySaved = moneySaved;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "History{" +
            "userEmail='" + userEmail + '\'' +
            ", activity='" + activity + '\'' +
            ", co2Saved=" + co2Saved +
            ", moneySaved=" + moneySaved +
            '}';
    }
}
