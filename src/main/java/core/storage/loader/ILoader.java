package core.storage.loader;

/**
 * The interface Loader.
 *
 * @param <T> type of the save/load object
 */
public interface ILoader<T> {

    /**
     * Save boolean.
     *
     * @param col the col
     * @return the boolean
     */
    boolean save(T col);

    /**
     * Load t.
     *
     * @return the t
     */
    T load() ;
}
