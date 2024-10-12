package engine;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EngineDB implements EngineRepository {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/java_labs";
    private static final String USER = "postgres";
    private static final String PASS = "QazWsx@Edc1234";


    // Метод для підключення до БД
    private Connection connect() throws SQLException {
        // Повертає з'єднання з базою даних, використовуючи вказані URL, користувача та пароль
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public EngineDB() throws SQLException {
    }

    @Override
    public List<Engine> getAll() throws Exception {
        List<Engine> engines = new ArrayList<>();
        String query = "SELECT * FROM engines";
        try (Connection conn = connect(); // Підключення до бази даних
             Statement stmt = conn.createStatement(); // Підключення до бази даних
             ResultSet rs = stmt.executeQuery(query)) {  // Виконання запиту та отримання результатів

            System.out.println("Список двигунів:");
            // Обхід усіх записів результату запиту
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
            return engines;
        }
    }

    @Override
    public void add(Engine e) throws Exception {
        String engineType = e.getEngineType();
        int horsepower = e.getHorsepower();
        String query = "INSERT INTO engines (engine_type, horsepower) VALUES (?, ?)";
        try (Connection conn = connect();  // Підключення до бази даних
             PreparedStatement pstmt = conn.prepareStatement(query)) { // Підготовка запиту

            // Встановлення значень для запиту
            pstmt.setString(1, engineType); // Перший параметр: тип двигуна
            pstmt.setInt(2, horsepower);  // Другий параметр: потужність
            pstmt.executeUpdate();  // Виконання запиту для вставки даних

        } catch (SQLException ex) {
            // Обробка помилки під час додавання двигуна
            System.out.println("Помилка під час додавання двигуна: " + ex.getMessage());
        }
    }

    @Override
    public void update(Engine e) throws Exception {
        String query = "UPDATE engines SET engine_type = ?, horsepower = ? WHERE id = ?";
        try (Connection conn = connect();  // Підключення до бази даних
             PreparedStatement pstmt = conn.prepareStatement(query)) {  // Підготовка запиту

            // Встановлення нових значень для оновлення
            pstmt.setString(1, e.getEngineType()); // Новий тип двигуна
            pstmt.setInt(2, e.getHorsepower()); // Нова потужність
            pstmt.setInt(3, e.getId()); // ID двигуна для оновлення

            int rowsAffected = pstmt.executeUpdate(); // Виконання запиту
            // Перевірка, чи було оновлено запис
            if (rowsAffected > 0) {
                System.out.println("Двигун успішно оновлено.");
            } else {
                System.out.println("Двигун з таким ID не знайдено.");
            }

        } catch (SQLException ex) {
            // Обробка помилки під час оновлення двигуна
            System.out.println("Помилка під час оновлення двигуна: " + ex.getMessage());
        }
    }

    @Override
    public void delete(int id) throws Exception {
        // SQL-запит для видалення запису з таблиці 'engines' за ID
        String query = "DELETE FROM engines WHERE id = ?";
        try (Connection conn = connect();  // Підключення до бази даних
             PreparedStatement pstmt = conn.prepareStatement(query)) {  // Підготовка запиту

            pstmt.setInt(1, id); // Встановлення ID для видалення
            int rowsAffected = pstmt.executeUpdate(); // Виконання запиту
            // Перевірка, чи було видалено запис
            if (rowsAffected > 0) {
                System.out.println("Двигун успішно видалено.");
            } else {
                System.out.println("Двигун з таким ID не знайдено.");
            }

        } catch (SQLException e) {
            // Обробка помилки під час видалення двигуна
            System.out.println("Помилка під час видалення двигуна: " + e.getMessage());
        }
    }

    @Override
    public void save(List<Engine> e) throws Exception {

    }

    @Override
    public List<Engine> displayEnginesFromBinFile(String filename) throws Exception {
        return List.of();
    }

}
