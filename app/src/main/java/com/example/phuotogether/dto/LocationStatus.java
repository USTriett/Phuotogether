package com.example.phuotogether.dto;
import java.io.Serializable;
public class LocationStatus implements Serializable{
    private int locationID;
    private String dateTime;
    private int temperature;
    private int moisture;
    private int rainProb;
    private int density;

    // Constructor
    public LocationStatus(int locationID, String dateTime, int temperature, int moisture, int rainProb, int density) {
        this.locationID = locationID;
        this.dateTime = dateTime;
        this.temperature = temperature;
        this.moisture = moisture;
        this.rainProb = rainProb;
        this.density = density;
    }

    // Getter
    public int getLocationID() {
        return locationID;
    }
    public String getDateTime() {
        return dateTime;
    }
    public int getTemperature() {
        return temperature;
    }
    public int getMoisture() {
        return moisture;
    }
    public int getRainProb() {
        return rainProb;
    }
    public int getDensity() {
        return density;
    }

    // Setter
    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }
    public void setMoisture(int moisture) {
        this.moisture = moisture;
    }
    public void setRainProb(int rainProb) {
        this.rainProb = rainProb;
    }
    public void setDensity(int density) {
        this.density = density;
    }
}
