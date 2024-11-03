/**
 * Represents a database access layer for engine management.
 * This class implements the EngineRepository interface and provides methods
 * to perform CRUD operations on the engine records in the PostgreSQL database.
 */
package engine;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EngineDB implements EngineRepository {
    // Database connection parameters
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/java_labs";
    private static final String USER = "postgres";
    private static final String PASS = "QazWsx@Edc1234";

    /**
     * Establishes a connection to the database.
     *
     * @return a Connection object for interacting with the database.
     * @throws SQLException if a database access error occurs.
     */
    private Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    /**
     * Default constructor for EngineDB.
     * Initializes the database connection parameters.
     *
     * @throws SQLException if a database access error occurs.
     */
    public EngineDB() throws SQLException {
    }

    /**
     * Retrieves a list of all engines from the database.
     *
     * @return a List of Engine objects.
     * @throws Exception if a database access error occurs.
     */
    @Override
    public List<Engine> getAll() throws Exception {
        List<Engine> engines = new ArrayList<>();
        String query = "SELECT * FROM engines";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("Список двигунів:");
            while (rs.next()) {
                Engine engine = new Engine(
                        rs.getInt("id"),
                        rs.getString("engine_type"),
                        rs.getInt("horsepower")
                );
                engines.add(engine);
            }
            return engines;
        } catch (SQLException e) {
            return engines; // Return empty list if an error occurs
        }
    }

    /**
     * Adds a new engine record to the database.
     *
     * @param e the Engine object to be added.
     * @throws Exception if a database access error occurs.
     */
    @Override
    public void add(Engine e) throws Exception {
        String engineType = e.getEngineType();
        int horsepower = e.getHorsepower();
        String query = "INSERT INTO engines (engine_type, horsepower) VALUES (?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, engineType);
            pstmt.setInt(2, horsepower);
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Помилка під час додавання двигуна: " + ex.getMessage());
        }
    }

    /**
     * Updates an existing engine record in the database.
     *
     * @param e the Engine object containing updated information.
     * @throws Exception if a database access error occurs.
     */
    @Override
    public void update(Engine e) throws Exception {
        String query = "UPDATE engines SET engine_type = ?, horsepower = ? WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, e.getEngineType());
            pstmt.setInt(2, e.getHorsepower());
            pstmt.setInt(3, e.getId());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Двигун успішно оновлено.");
            } else {
                System.out.println("Двигун з таким ID не знайдено.");
            }

        } catch (SQLException ex) {
            System.out.println("Помилка під час оновлення двигуна: " + ex.getMessage());
        }
    }

    /**
     * Deletes an engine record from the database by its ID.
     *
     * @param id the ID of the engine to be deleted.
     * @throws Exception if a database access error occurs.
     */
    @Override
    public void delete(int id) throws Exception {
        String query = "DELETE FROM engines WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Двигун успішно видалено.");
            } else {
                System.out.println("Двигун з таким ID не знайдено.");
            }

        } catch (SQLException e) {
            System.out.println("Помилка під час видалення двигуна: " + e.getMessage());
        }
    }

    @Override
    public void save(List<Engine> e) throws Exception {
        // Method not implemented
    }

    @Override
    public List<Engine> displayEnginesFromBinFile(String filename) throws Exception {
        return List.of(); // Method not implemented
    }

}
