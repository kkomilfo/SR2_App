package vehicle;

public class TransportVehicle {
    // Приватні поля класу для збереження моделі та року випуску транспортного засобу
    private String model;
    private int year;

    // Конструктор без параметрів
    public TransportVehicle() {
        this.model = "Unknown";
        this.year = 2000;
    }

    // Конструктор з параметрами
    public TransportVehicle(String model, int year) {
        this.model = model;
        this.year = year;
    }

    // Метод для виведення інформації
    public void showInfo() {
        System.out.println("Транспортний засіб: " + model + ", рік випуску: " + year);
    }

    // Геттери та сеттери
    // Геттер для отримання значення моделі
    public String getModel() {
        return model;
    }

    // Сеттер для встановлення нового значення моделі
    public void setModel(String model) {
        this.model = model;
    }

    // Геттер для отримання значення року випуску
    public int getYear() {
        return year;
    }

    // Сеттер для встановлення нового значення року випуску
    public void setYear(int year) {
        this.year = year;
    }
}
