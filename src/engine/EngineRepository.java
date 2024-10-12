package engine;

import java.util.List;

public interface EngineRepository {
    List<Engine> getAll() throws Exception;
    void add(Engine e) throws Exception;
    void update(Engine e) throws Exception;
    void save(List<Engine> e) throws Exception;
    List<Engine> displayEnginesFromBinFile(String filename) throws Exception;
}
