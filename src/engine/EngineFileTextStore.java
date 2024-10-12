package engine;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EngineFileTextStore implements EngineRepository {
    private final String filePath;

    public EngineFileTextStore(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Engine> getAll() throws Exception {
        List<Engine> engines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    int id = Integer.parseInt(parts[0]);
                    String engineType = parts[1];
                    int horsepower = Integer.parseInt(parts[2]);
                    engines.add(new Engine(id, engineType, horsepower));
                }
            }
        } catch (IOException e) {
            throw new IOException(e);
        }
        return engines;
    }

    @Override
    public void add(Engine e) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(String.format(e.getId() + "," + e.getEngineType() + "," + e.getHorsepower()));
            writer.newLine();
        } catch (IOException ex) {
            throw new IOException(ex);
        }
    }

    @Override
    public void update(Engine e) throws Exception {
        List<Engine> engines = getAll();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Engine engine : engines) {
                if (engine.getEngineType().equals(e.getEngineType())) {
                    writer.write(String.format(e.getEngineType() + "," + e.getHorsepower()));
                } else {
                    writer.write(engine.toString());
                }
                writer.newLine();
            }
        } catch (IOException ex) {
            throw new IOException(ex);
        }
    }

    public void save(List<Engine> engines) throws Exception {
        saveEnginesToFile(engines);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("sorted_data.txt"))) {
            for (Engine engine : engines) {
                writer.write(engine.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new Exception("Failed to save engines to file: " + e.getMessage());
        }
    }

    public void saveEnginesToFile(List<Engine> engines) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("sorted_data.bin"))) {
            for (Engine engine : engines) {
                oos.writeObject(engine);
            }
            System.out.println("Дані успішно збережено у файл.");
        } catch (IOException e) {

            System.out.println("Помилка під час запису у файл: " + e.getMessage());
        }
    }


    public List<Engine> displayEnginesFromBinFile(String filename) throws Exception {
        List<Engine> engines = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            System.out.println("Дані з файлу:");
            // Читання даних про двигуни до кінця файлу
            while (true) {
                try {
                    Engine engine = (Engine) ois.readObject();
                    engines.add(engine);
                    // Виведення інформації про кожен двигун
                    System.out.println("Тип двигуна: " + engine.getEngineType() + ", Потужність: " + engine.getHorsepower());
                } catch (ClassNotFoundException e) {
                    System.out.println("Помилка: клас не знайдено - " + e.getMessage());
                    break;
                } catch (IOException e) {
                    // Кінець файлу
                    break;
                }
            }
        } catch (IOException e) {
            // Обробка помилки при зчитуванні файлу
            System.out.println("Помилка під час зчитування файлу: " + e.getMessage());
        }
        return engines;
    }

    @Override
    public void delete(int id) throws Exception {

    }
}