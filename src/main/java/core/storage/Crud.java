package core.storage;

import java.util.Collection;

public interface Crud<ID, Entity> {

    /**
     * Add.
     * @param e the e
     */
    void add(Entity e);

    /**
     * Remove boolean.
     *
     * @param id the id
     * @return the boolean
     */
    boolean removeById(ID id);

    /**
     * Update boolean.
     *
     * @param entity the entity
     * @return the boolean
     */
    boolean update(Entity entity);


    boolean dumpToTheFile();

    Collection<Entity> readAll();




}
