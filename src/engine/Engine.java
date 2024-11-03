
package engine;

import java.io.Serializable;

/**
 * Represents an engine of a vehicle.
 * This class provides information about the engine type and its horsepower.
 * It implements Serializable to allow engine objects to be serialized.
 */
public class Engine implements Serializable {
    // Unique identifier for the engine
    private final int id;
    // Type of the engine (e.g., petrol, diesel, electric)
    private String engineType;
    // Horsepower of the engine
    private int horsepower;

    /**
     * Default constructor initializing engine with default values.
     * ID is set to 0, engine type to "Unknown", and horsepower to 0.
     */
    public Engine() {
        this.id = 0;
        this.engineType = "Unknown";
        this.horsepower = 0;
    }

    /**
     * Constructor that initializes the engine with specified values.
     *
     * @param id          The unique identifier for the engine.
     * @param engineType  The type of the engine.
     * @param horsepower  The horsepower of the engine.
     */
    public Engine(int id, String engineType, int horsepower) {
        this.id = id;
        this.engineType = engineType;
        this.horsepower = horsepower;
    }

    /**
     * Displays engine information to the console.
     * Currently, it does not implement any output functionality.
     */
    public void showEngineInfo() {
        System.out.println();
    }

    /**
     * Returns the unique identifier of the engine.
     *
     * @return the ID of the engine.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns a formatted string with engine information.
     *
     * @return a string containing the engine's ID, type, and horsepower.
     */
    public String getInfo() {
        return String.format("ID: " + id + ", Двигун: " + engineType + ", потужність: " + horsepower + " к.с.");
    }

    /**
     * Returns the type of the engine.
     *
     * @return the engine type.
     */
    public String getEngineType() {
        return engineType;
    }

    /**
     * Sets the type of the engine.
     *
     * @param engineType the new type of the engine.
     */
    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    /**
     * Returns the horsepower of the engine.
     *
     * @return the horsepower of the engine.
     */
    public int getHorsepower() {
        return horsepower;
    }

    /**
     * Sets the horsepower of the engine.
     *
     * @param horsepower the new horsepower of the engine.
     */
    public void setHorsepower(int horsepower) {
        this.horsepower = horsepower;
    }

    /**
     * Returns a string representation of the engine.
     *
     * @return a string with the engine's information.
     */
    @Override
    public String toString() {
        return getInfo();
    }
}
