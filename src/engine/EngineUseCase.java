package engine;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * The EngineUseCase class provides a set of operations to manage engine records,
 * utilizing the EngineRepository interface for data storage and retrieval.
 * This class includes methods for creating, updating, deleting, filtering,
 * and displaying engine records.
 */
public class EngineUseCase {
    private final EngineRepository engineRepository;

    /**
     * Constructs an EngineUseCase with a specified EngineRepository.
     *
     * @param engineRepository the repository to interact with engine data.
     */
    public EngineUseCase(EngineRepository engineRepository) {
        this.engineRepository = engineRepository;
    }

    /**
     * Retrieves all engine records from the repository.
     *
     * @return a List of all Engine objects.
     * @throws Exception if an error occurs during retrieval.
     */
    public List<Engine> getEngines() throws Exception {
        return engineRepository.getAll();
    }

    /**
     * Creates a new engine record and adds it to the repository.
     *
     * @param engineType the type of the engine.
     * @param enginePower the horsepower of the engine as a string.
     * @throws Exception if an error occurs during the creation.
     */
    public void tryCreateEngine(String engineType, String enginePower) throws Exception {
        int enginePowerNumber = Integer.parseInt(enginePower);
        int id = engineRepository.getAll().size();
        Engine engine = new Engine(id, engineType, enginePowerNumber);
        engineRepository.add(engine);
    }

    /**
     * Updates an existing engine record in the repository.
     *
     * @param engine the Engine object containing updated information.
     * @throws Exception if an error occurs during the update.
     */
    public void update(Engine engine) throws Exception {
        engineRepository.update(engine);
    }

    /**
     * Filters engine records by horsepower.
     *
     * @param horsePower the horsepower threshold as a string.
     * @return a List of Engine objects with horsepower greater than the specified value.
     * @throws Exception if an error occurs during filtering.
     */
    public List<Engine> filterByHorsePower(String horsePower) throws Exception {
        int horsePowerNumber = Integer.parseInt(horsePower);
        return engineRepository.getAll()
                .stream()
                .filter(engine -> engine.getHorsepower() > horsePowerNumber)
                .toList();
    }

    /**
     * Displays the engines with the minimum and maximum horsepower.
     *
     * @return a List containing the Engine objects with the lowest and highest horsepower.
     * @throws Exception if an error occurs during retrieval.
     */
    public List<Engine> displayMinMaxPowerEngines() throws Exception {
        List<Engine> engines = engineRepository.getAll();
        Optional<Engine> minEngine = engines.stream().min(Comparator.comparingInt(Engine::getHorsepower));
        Optional<Engine> maxEngine = engines.stream().max(Comparator.comparingInt(Engine::getHorsepower));

        if (minEngine.isPresent() && maxEngine.isPresent()) {
            return new ArrayList<>() {{
                add(minEngine.get());
                add(maxEngine.get());
            }};
        } else {
            return List.of();
        }
    }

    /**
     * Displays engines that match a specified regex pattern.
     *
     * @param regex the regex pattern to match engine types.
     * @return a List of Engine objects that match the pattern.
     * @throws Exception if an error occurs during retrieval.
     */
    public List<Engine> displayEnginesMatchingPattern(String regex) throws Exception {
        Pattern pattern = Pattern.compile(regex);
        return getEngines().stream()
                .filter(engine -> pattern.matcher(engine.getEngineType()).find())
                .collect(Collectors.toList());
    }

    /**
     * Saves a list of engines to the repository after sorting them by horsepower.
     *
     * @param engines the List of Engine objects to save.
     * @throws Exception if an error occurs during saving.
     */
    public void save(List<Engine> engines) throws Exception {
        engines.sort(Comparator.comparingInt(Engine::getHorsepower));
        engineRepository.save(engines);
    }

    /**
     * Displays engine records from a binary file.
     *
     * @return a List of Engine objects read from the binary file.
     * @throws Exception if an error occurs during file reading.
     */
    public List<Engine> displayEnginesFromBinFile() throws Exception {
        return engineRepository.displayEnginesFromBinFile("sorted_data.bin");
    }

    /**
     * Deletes an engine record from the repository by its ID.
     *
     * @param id the unique identifier of the engine to delete.
     * @throws Exception if an error occurs during deletion.
     */
    public void delete(int id) throws Exception {
        engineRepository.delete(id);
    }
}
