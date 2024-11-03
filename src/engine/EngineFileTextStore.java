/**
 * Manages engine records using a text file as storage.
 * This class implements the EngineRepository interface and provides methods
 * to perform CRUD operations on engine records stored in a text file.
 */
package engine;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EngineFileTextStore implements EngineRepository {
    private final String filePath;

    /**
     * Constructs an EngineFileTextStore with the specified file path.
     *
     * @param filePath the path to the text file for storing engine data.
     */
    public EngineFileTextStore(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Retrieves all engine records from the text file.
     *
     * @return a List of Engine objects read from the file.
     * @throws Exception if an error occurs while reading the file.
     */
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

    /**
     * Adds a new engine record to the text file.
     *
     * @param e the Engine object to be added.
     * @throws IOException if an error occurs while writing to the file.
     */
    @Override
    public void add(Engine e) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(String.format(e.getId() + "," + e.getEngineType() + "," + e.getHorsepower()));
            writer.newLine();
        } catch (IOException ex) {
            throw new IOException(ex);
        }
    }

    /**
     * Updates an existing engine record in the text file.
     *
     * @param e the Engine object containing updated information.
     * @throws Exception if an error occurs while updating the file.
     */
    @Override
    public void update(Engine e) throws Exception {
        List<Engine> engines = getAll();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Engine engine : engines) {
                if (engine.getId() == e.getId()) {
                    writer.write(String.format(e.getId() + "," + e.getEngineType() + "," + e.getHorsepower()));
                } else {
                    writer.write(engine.toString());
                }
                writer.newLine();
            }
        } catch (IOException ex) {
            throw new IOException(ex);
        }
    }

    /**
     * Saves a list of engines to both a text file and a binary file.
     *
     * @param engines the List of Engine objects to save.
     * @throws Exception if an error occurs while saving engines to file.
     */
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

    /**
     * Saves a list of engines to a binary file.
     *
     * @param engines the List of Engine objects to save.
     */
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

    /**
     * Displays and retrieves engines stored in a binary file.
     *
     * @param filename the name of the binary file to read from.
     * @return a List of Engine objects read from the binary file.
     * @throws Exception if an error occurs while reading the file.
     */
    public List<Engine> displayEnginesFromBinFile(String filename) throws Exception {
        List<Engine> engines = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            System.out.println("Дані з файлу:");
            while (true) {
                try {
                    Engine engine = (Engine) ois.readObject();
                    engines.add(engine);
                    System.out.println("Тип двигуна: " + engine.getEngineType() + ", Потужність: " + engine.getHorsepower());
                } catch (ClassNotFoundException e) {
                    System.out.println("Помилка: клас не знайдено - " + e.getMessage());
                    break;
                } catch (IOException e) {
                    break; // End of file reached
                }
            }
        } catch (IOException e) {
            System.out.println("Помилка під час зчитування файлу: " + e.getMessage());
        }
        return engines;
    }

    @Override
    public void delete(int id) throws Exception {
        // Method not implemented
    }
}
