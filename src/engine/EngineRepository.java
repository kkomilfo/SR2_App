
package engine;

import java.util.List;

/**
 * Interface for managing engine records in various storage systems.
 * This interface defines the necessary methods for CRUD (Create, Read, Update, Delete) operations
 * on engine records, allowing different implementations (e.g., in-memory, file-based, database).
 */
public interface EngineRepository {

    /**
     * Retrieves all engine records from the storage.
     *
     * @return a List of Engine objects.
     * @throws Exception if an error occurs during retrieval.
     */
    List<Engine> getAll() throws Exception;

    /**
     * Adds a new engine record to the storage.
     *
     * @param e the Engine object to be added.
     * @throws Exception if an error occurs during the addition.
     */
    void add(Engine e) throws Exception;

    /**
     * Updates an existing engine record in the storage.
     *
     * @param e the Engine object containing updated information.
     * @throws Exception if an error occurs during the update.
     */
    void update(Engine e) throws Exception;

    /**
     * Saves a list of engine records to the storage.
     *
     * @param e the List of Engine objects to save.
     * @throws Exception if an error occurs during the save operation.
     */
    void save(List<Engine> e) throws Exception;

    /**
     * Displays engine records from a binary file.
     *
     * @param filename the name of the binary file to read from.
     * @return a List of Engine objects read from the file.
     * @throws Exception if an error occurs during file reading.
     */
    List<Engine> displayEnginesFromBinFile(String filename) throws Exception;

    /**
     * Deletes an engine record from the storage by its ID.
     *
     * @param id the unique identifier of the engine to delete.
     * @throws Exception if an error occurs during the deletion.
     */
    void delete(int id) throws Exception;
}
