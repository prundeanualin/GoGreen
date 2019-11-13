package gogreenserver.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "records")
public class Records {

    // Define Fields
    @Id
    @Column(name = "username")
    private String userName;

    @Column(name = "kwh_produced_solarpanels")
    private Float kwhProducedSolarpanels;

    @Column(name = "saved_kwh_energy")
    private Float savedKwhEnergy;

    @Column(name = "saved_co2_solarpanels")
    private Float savedCo2Solarpanels;

    @Column(name = "saved_co2_food")
    private Float savedCo2Food;

    @Column(name = "saved_co2_energy")
    private Float savedCo2Energy;

    @Column(name = "saved_co2_transport")
    private Float savedCo2Transport;

    @Column(name = "saved_co2_total")
    private Float savedCo2Total;

    @Column(name = "saved_price_food")
    private Float savedPriceFood;

    @Column(name = "saved_price_solarpanels")
    private Float savedPriceSolarpanels;

    @Column(name = "saved_price_transport")
    private Float savedPriceTransport;

    @Column(name = "saved_price_energy")
    private Float savedPriceEnergy;

    @Column(name = "saved_price_total")
    private Float savedPriceTotal;

    @Column(name = "saved_co2_trees")
    private Float savedCo2Tree;

    public Float getSavedCo2Tree() {
        return savedCo2Tree;
    }

    public void setSavedCo2Tree(Float savedCo2tree) {
        this.savedCo2Tree = savedCo2tree;
    }

    // Define Getters/Setters for JACKSON
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Float getKwhProducedSolarpanels() {
        return kwhProducedSolarpanels;
    }

    public void setKwhProducedSolarpanels(Float kwhProducedSolarpanels) {
        this.kwhProducedSolarpanels = kwhProducedSolarpanels;
    }

    public Float getSavedKwhEnergy() {
        return savedKwhEnergy;
    }

    public void setSavedKwhEnergy(Float savedKwhEnergy) {
        this.savedKwhEnergy = savedKwhEnergy;
    }

    public Float getSavedCo2Solarpanels() {
        return savedCo2Solarpanels;
    }

    public void setSavedCo2Solarpanels(Float savedCo2Solarpanels) {
        this.savedCo2Solarpanels = savedCo2Solarpanels;
    }

    public Float getSavedCo2Food() {
        return savedCo2Food;
    }

    public void setSavedCo2Food(Float savedCo2Food) {
        this.savedCo2Food = savedCo2Food;
    }

    public Float getSavedCo2Energy() {
        return savedCo2Energy;
    }

    public void setSavedCo2Energy(Float savedCo2Energy) {
        this.savedCo2Energy = savedCo2Energy;
    }

    public Float getSavedCo2Transport() {
        return savedCo2Transport;
    }

    public void setSavedCo2Transport(Float savedCo2Transport) {
        this.savedCo2Transport = savedCo2Transport;
    }

    public Float getSavedCo2Total() {
        return savedCo2Total;
    }

    public void setSavedCo2Total(Float savedCo2Total) {
        this.savedCo2Total = savedCo2Total;
    }

    public Float getSavedPriceFood() {
        return savedPriceFood;
    }

    public void setSavedPriceFood(Float savedPriceFood) {
        this.savedPriceFood = savedPriceFood;
    }

    public Float getSavedPriceSolarpanels() {
        return savedPriceSolarpanels;
    }

    public void setSavedPriceSolarpanels(Float savedPriceSolarpanels) {
        this.savedPriceSolarpanels = savedPriceSolarpanels;
    }

    public Float getSavedPriceTransport() {
        return savedPriceTransport;
    }

    public void setSavedPriceTransport(Float savedPriceTransport) {
        this.savedPriceTransport = savedPriceTransport;
    }

    public Float getSavedPriceEnergy() {
        return savedPriceEnergy;
    }

    public void setSavedPriceEnergy(Float savedPriceEnergy) {
        this.savedPriceEnergy = savedPriceEnergy;
    }

    public Float getSavedPriceTotal() {
        return savedPriceTotal;
    }

    public void setSavedPriceTotal(Float savedPriceTotal) {
        this.savedPriceTotal = savedPriceTotal;
    }

    // Define toString
    @Override
    public String toString() {
        return "Records{"
            + "userName='" + userName + '\''
            + ", kwhProducedSolarpanels=" + kwhProducedSolarpanels
            + ", savedKwhEnergy=" + savedKwhEnergy
            + ", savedCo2Solarpanels=" + savedCo2Solarpanels
            + ", savedCo2Food=" + savedCo2Food
            + ", savedCo2Energy=" + savedCo2Energy
            + ", savedCo2Transport=" + savedCo2Transport
            + ", savedCo2Tree=" + savedCo2Tree
            + ", savedCo2Total=" + savedCo2Total
            + ", savedPriceFood=" + savedPriceFood
            + ", savedPriceSolarpanels=" + savedPriceSolarpanels
            + ", savedPriceTransport=" + savedPriceTransport
            + ", savedPriceEnergy=" + savedPriceEnergy
            + ", savedPriceTotal=" + savedPriceTotal
            + '}';
    }
}
