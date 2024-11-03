/**
 * Manages engine records in memory.
 * This class implements the EngineRepository interface and provides methods
 * to perform CRUD operations on engine records stored in a List.
 */
package engine;

import java.util.ArrayList;
import java.util.List;

public class EngineInMemoryStore implements EngineRepository {
    private final List<Engine> engines;

    /**
     * Constructs an EngineInMemoryStore with a predefined list of engine records.
     * Initializes the in-memory storage with some sample engines.
     */
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

    /**
     * Retrieves all engine records from the in-memory storage.
     *
     * @return a List of Engine objects stored in memory.
     */
    @Override
    public List<Engine> getAll() {
        return engines;
    }

    /**
     * Adds a new engine record to the in-memory storage.
     *
     * @param e the Engine object to be added.
     */
    @Override
    public void add(Engine e) {
        engines.add(e);
    }

    /**
     * Updates an existing engine record in the in-memory storage.
     *
     * @param e the Engine object containing updated information.
     */
    @Override
    public void update(Engine e) {
        engines.set(engines.indexOf(e), e);
    }

    /**
     * This method is not supported in in-memory storage.
     *
     * @param e the List of Engine objects to save.
     * @throws Exception if this method is called.
     */
    @Override
    public void save(List<Engine> e) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * This method is not implemented for in-memory storage.
     *
     * @param filename the name of the binary file to read from.
     * @return an empty list since this functionality is not supported.
     * @throws Exception if this method is called.
     */
    @Override
    public List<Engine> displayEnginesFromBinFile(String filename) throws Exception {
        return List.of();
    }

    /**
     * Deletes an engine record from the in-memory storage by ID.
     *
     * @param id the unique identifier of the engine to delete.
     * @throws Exception if the delete operation is not supported yet.
     */
    @Override
    public void delete(int id) throws Exception {
        // Method not implemented
    }
}
