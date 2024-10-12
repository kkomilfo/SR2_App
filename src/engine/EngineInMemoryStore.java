package engine;

import java.util.ArrayList;
import java.util.List;

public class EngineInMemoryStore implements EngineRepository {
    private final List<Engine> engines;

    public EngineInMemoryStore() {
        engines = new ArrayList<>() {{
            add(new Engine(1, "v1 Diesel", 120));
            add(new Engine(2,"v2 Petrol", 100));
            add(new Engine(3,"v3 Electric", 200));
            add(new Engine(4, "v4 Hybrid", 150));
            add(new Engine(5, "v5 Petrol1", 115));
            add(new Engine(6, "v6 Diesel1", 110));
            add(new Engine(7, "v7 Electric1", 180));
            add(new Engine(8, "v8 Hybrid1", 140));
            add(new Engine(9, "v9 Petrol2", 156));
            add(new Engine(10, "v10 Diesel2", 130));
        }};
    }

    @Override
    public List<Engine> getAll() {
        return engines;
    }

    @Override
    public void add(Engine e) {
        engines.add(e);
    }

    @Override
    public void update(Engine e) {
        engines.set(engines.indexOf(e), e);
    }
}
