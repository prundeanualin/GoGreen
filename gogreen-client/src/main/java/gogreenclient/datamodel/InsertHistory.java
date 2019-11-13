package gogreenclient.datamodel;

import java.time.LocalDateTime;


public class InsertHistory {


    private LocalDateTime insertDate;

    private String userName;

    private String activityName;

    private Float activityPrice;


    private String alternateActivity;

    private Float alternateActivityPrice;

    private Boolean activityIsLocalproduce;

    private Boolean alternateActivityIsLocalproduce;

    private Float transportDistanceKm;

    private Float energyActivityDurationMinutes;

    private Float energyActivityTempAreaM2;

    private Float energyActivityTempDegreesDecreased;

    /**
     * Constructor of the user model. Some fields will be set to its default value, you can use
     * setters to change it's value we is needed.
     *
     * @param userName user name.
     */
    public InsertHistory(String userName) {
        this.userName = userName;
        this.activityName = "login";
        this.insertDate = LocalDateTime.now();
        this.activityPrice = null;
        this.alternateActivity = null;
        this.alternateActivityPrice = null;
        this.activityIsLocalproduce = false;
        this.alternateActivityIsLocalproduce = false;
        this.transportDistanceKm = null;
        this.energyActivityDurationMinutes = null;
        this.energyActivityTempAreaM2 = null;
        this.energyActivityTempDegreesDecreased = null;
    }

    public LocalDateTime getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(LocalDateTime insertDate) {
        this.insertDate = insertDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Float getActivityPrice() {
        return activityPrice;
    }

    public void setActivityPrice(Float activityPrice) {
        this.activityPrice = activityPrice;
    }

    public String getAlternateActivity() {
        return alternateActivity;
    }

    public void setAlternateActivity(String alternateActivity) {
        this.alternateActivity = alternateActivity;
    }

    public Float getAlternateActivityPrice() {
        return alternateActivityPrice;
    }

    public void setAlternateActivityPrice(Float alternateActivityPrice) {
        this.alternateActivityPrice = alternateActivityPrice;
    }

    public Boolean getActivityIsLocalproduce() {
        return activityIsLocalproduce;
    }

    public void setActivityIsLocalproduce(Boolean activityIsLocalproduce) {
        this.activityIsLocalproduce = activityIsLocalproduce;
    }

    public Boolean getAlternateActivityIsLocalproduce() {
        return alternateActivityIsLocalproduce;
    }

    public void setAlternateActivityIsLocalproduce(Boolean alternateActivityIsLocalproduce) {
        this.alternateActivityIsLocalproduce = alternateActivityIsLocalproduce;
    }

    public Float getTransportDistanceKm() {
        return transportDistanceKm;
    }

    public void setTransportDistanceKm(Float transportDistanceKm) {
        this.transportDistanceKm = transportDistanceKm;
    }

    public Float getEnergyActivityDurationMinutes() {
        return energyActivityDurationMinutes;
    }

    public void setEnergyActivityDurationMinutes(Float energyActivityDurationMinutes) {
        this.energyActivityDurationMinutes = energyActivityDurationMinutes;
    }

    public Float getEnergyActivityTempAreaM2() {
        return energyActivityTempAreaM2;
    }

    public void setEnergyActivityTempAreaM2(Float energyActivityTempAreaM2) {
        this.energyActivityTempAreaM2 = energyActivityTempAreaM2;
    }

    public Float getEnergyActivityTempDegreesDecreased() {
        return energyActivityTempDegreesDecreased;
    }

    public void setEnergyActivityTempDegreesDecreased(Float energyActivityTempDegreesDecreased) {
        this.energyActivityTempDegreesDecreased = energyActivityTempDegreesDecreased;
    }


    // Define toString


    @Override
    public String toString() {
        return "InsertHistory{"
            + "userName='" + userName + '\''
            + ", activityName='" + activityName + '\''
            + ", activityPrice=" + activityPrice
            + ", alternateActivity='" + alternateActivity + '\''
            + ", alternateActivityPrice=" + alternateActivityPrice
            + ", activityIsLocalproduce=" + activityIsLocalproduce
            + ", alternateActivityIsLocalproduce=" + alternateActivityIsLocalproduce
            + ", transportDistanceKm=" + transportDistanceKm
            + ", energyActivityDurationMinutes=" + energyActivityDurationMinutes
            + ", energyActivityTempAreaM2=" + energyActivityTempAreaM2
            + ", energyActivityTempDegreesDecreased=" + energyActivityTempDegreesDecreased
            + '}';
    }

}
