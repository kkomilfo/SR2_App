package engine;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class EngineUseCase {
    private final EngineRepository engineRepository;

    public EngineUseCase(EngineRepository engineRepository) {
        this.engineRepository = engineRepository;
    }

    public List<Engine> getEngines() throws Exception {
        return engineRepository.getAll();
    }

    public void tryCreateEngine(String engineType, String enginePower) throws Exception {
        int enginePowerNumber = Integer.parseInt(enginePower);
        int id = engineRepository.getAll().size();
        Engine engine = new Engine(id, engineType, enginePowerNumber);
        engineRepository.add(engine);
    }

    public void update(Engine engine) throws Exception {
        engineRepository.update(engine);
    }

    public List<Engine> filterByHorsePower(String horsePower) throws Exception {
        int horsePowerNumber = Integer.parseInt(horsePower);
        return engineRepository.getAll()
                .stream()
                .filter(engine -> engine.getHorsepower() > horsePowerNumber)
                .toList();
    }


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

    public List<Engine> displayEnginesMatchingPattern(String regex) throws Exception {
        Pattern pattern = Pattern.compile(regex);
        return getEngines().stream()
                .filter(engine -> pattern.matcher(engine.getEngineType()).find())
                .collect(Collectors.toList());
    }

    public void save(List<Engine> engines) throws Exception {
        engines.sort(Comparator.comparingInt(Engine::getHorsepower));
        engineRepository.save(engines);
    }

    public List<Engine> displayEnginesFromBinFile() throws Exception {
        return engineRepository.displayEnginesFromBinFile("sorted_data.bin");
    }
}
