package vehicle;

public class Car extends TransportVehicle implements FuelEfficiency {
    // Приватні поля для зберігання типу автомобіля і двигуна
    private String type;
    private Engine engine;  // Агреґуємо двигун (композиція)

    // Конструктор без параметрів
    public Car() {
        super(); // Викликаємо конструктор без параметрів з батьківського класу (TransportVehicle)
        this.type = "Unknown";
        this.engine = new Engine();  // Створюємо стандартний двигун
    }

    // Конструктор з параметрами
    public Car(String model, int year, String type, Engine engine) {
        super(model, year); // Викликаємо конструктор з параметрами з батьківського класу
        this.type = type; // Ініціалізуємо поле типу автомобіля
        this.engine = engine;  // Ініціалізуємо поле двигуна через агрегацію (передаємо існуючий двигун)
    }

    // Перевизначаємо метод showInfo з класу TransportVehicle для виведення додаткової інформації про автомобіль
    @Override
    public void showInfo() {
        System.out.println("Автомобіль: " + getModel() + ", рік випуску: " + getYear() + ", тип: " + type + " двигун: " + engine.getEngineType());
    }

    public String getInfo() {
        return String.format("Автомобіль: " + getModel() + ", рік випуску: " + getYear() + ", тип: " + type + " двигун: " + engine.getEngineType());
    }

    @Override
    public double calculateFuelEfficiency() {
        // Проста реалізація: повернемо фіксовану витрату пального
        return engine.getHorsepower() / 10.0; // Наприклад, чим більше к.с., тим більше витрата
    }

    // Геттери та сеттери для типу та двигуна
    // Геттер для отримання типу автомобіля
    public String getType() {
        return type;
    }

    // Сеттер для зміни типу автомобіля
    public void setType(String type) {
        this.type = type;
    }

    // Геттер для отримання двигуна автомобіля
    public Engine getEngine() {
        return engine;
    }

    // Сеттер для зміни двигуна автомобіля
    public void setEngine(Engine engine) {
        this.engine = engine;
    }
}
