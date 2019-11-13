package gogreenclient.datamodel;


public class Records {

    private String userName;

    private Float kwhProducedSolarpanels;

    private Float savedKwhEnergy;

    private Float savedCo2Solarpanels;

    private Float savedCo2Food;

    private Float savedCo2Energy;

    private Float savedCo2Transport;

    private Float savedCo2Total;

    private Float savedPriceFood;

    private Float savedPriceSolarpanels;

    private Float savedPriceTransport;

    private Float savedPriceEnergy;

    private Float savedPriceTotal;

    private Float savedCo2tree;

    /**.
     * return the saved emission from planting a tree
     * @return the value
     */
    public Float getSavedCo2tree() {
        if (savedCo2tree != null) {
            return savedCo2tree;
        } else {
            return 0.0F;
        }
    }

    public void setSavedCo2tree(Float savedCo2tree) {
        this.savedCo2tree = savedCo2tree;
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

}
