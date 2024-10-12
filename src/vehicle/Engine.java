package vehicle;

public class Engine {
    // Приватні поля для зберігання типу двигуна та його потужності
    private String engineType;
    private int horsepower;

    // Конструктор без параметрів
    public Engine() {
        // Ініціалізація значеннями за замовченням: невідомий тип двигуна і 0 кінських сил
        this.engineType = "Unknown";
        this.horsepower = 0;
    }

    // Конструктор з параметрами
    public Engine(String engineType, int horsepower) {
        // Ініціалізація полів переданими параметрами
        this.engineType = engineType;
        this.horsepower = horsepower;
    }

    // Метод для виведення інформації
    public void showEngineInfo() {
        System.out.println();
    }

    public String getInfo() {
        return String.format("Двигун: " + engineType + ", потужність: " + horsepower + " к.с.");
    }

    // Геттери та сеттери
    // Геттер для отримання типу двигуна
    public String getEngineType() {
        return engineType;
    }

    // Сеттер для встановлення типу двигуна
    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    // Геттер для отримання потужності двигуна
    public int getHorsepower() {
        return horsepower;
    }

    // Сеттер для зміни потужності двигуна
    public void setHorsepower(int horsepower) {
        this.horsepower = horsepower;
    }
}
