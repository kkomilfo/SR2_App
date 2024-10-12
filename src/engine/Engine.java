package engine;

import java.io.Serializable;

public class Engine implements Serializable {
    private final int id;
    private String engineType;
    private int horsepower;

    public Engine() {
        this.id = 0;
        this.engineType = "Unknown";
        this.horsepower = 0;
    }

    public Engine(int id, String engineType, int horsepower) {
        this.id = id;
        this.engineType = engineType;
        this.horsepower = horsepower;
    }

    public void showEngineInfo() {
        System.out.println();
    }

    public int getId() {
        return id;
    }

    public String getInfo() {
        return String.format("ID: " + id + ", Двигун: " + engineType + ", потужність: " + horsepower + " к.с.");
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public int getHorsepower() {
        return horsepower;
    }

    public void setHorsepower(int horsepower) {
        this.horsepower = horsepower;
    }

    @Override
    public String toString() {
        return getInfo();
    }
}
