package core.storage;

import java.time.LocalDateTime;

/**
 * The interface Auditable.
 *
 */
public interface Auditable {


    LocalDateTime getCreatedDate();

    String getCollectionType();

}
