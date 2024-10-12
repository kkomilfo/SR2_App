package engine;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EngineUseCase {
    private final EngineRepository engineRepository;

    public EngineUseCase(EngineRepository engineRepository) {
        this.engineRepository = engineRepository;
    }

    public List<Engine> getEngines() {
        return engineRepository.getAll();
    }

    public void tryCreateEngine(String engineType, String enginePower) throws Exception {
        int enginePowerNumber = Integer.parseInt(enginePower);
        int id = engineRepository.getAll().size();
        Engine engine = new Engine(id, engineType, enginePowerNumber);
        engineRepository.add(engine);
    }

    public List<Engine> FilterByHorsePower(String horsePower) throws Exception {
        int horsePowerNumber = Integer.parseInt(horsePower);
        return engineRepository.getAll()
                .stream()
                .filter(engine -> engine.getHorsepower() > horsePowerNumber)
                .toList();
    }

    public void Update(Engine engine) throws Exception {
        engineRepository.update(engine);
    }
}
