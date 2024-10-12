package engine;

import java.util.List;

public interface EngineRepository {
    List<Engine> getAll();
    void add(Engine e);
    void update(Engine e);
}
