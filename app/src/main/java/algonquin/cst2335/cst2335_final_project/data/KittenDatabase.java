/**
 * @author Mannan
 */
package algonquin.cst2335.cst2335_final_project.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;
/**
 * Represents a Room database that stores KittenImage objects.
 */
@Database(entities = {KittenImage.class}, version = 1)
public abstract class KittenDatabase extends RoomDatabase {

    /**
     * Returns a KittenDAO instance that provides methods for accessing the database.
     *
     * @return a KittenDAO instance that provides methods for accessing the database
     */
    public abstract KittenDAO cmDAO();
}
