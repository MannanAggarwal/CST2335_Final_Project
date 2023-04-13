/**

 The KittenDAO interface provides methods for performing database operations on the KittenImage entity.
 This interface is annotated with @Dao to indicate that it is a Data Access Object.
 @author Mannan
 */
package algonquin.cst2335.cst2335_final_project.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;


@Dao
public interface KittenDAO {

    /**
     * Inserts a KittenImage object into the database.
     *
     * @param k the KittenImage object to be inserted
     */
    @Insert
    void insertKittenItem(KittenImage k);

    /**
     * Retrieves all KittenImage objects from the database.
     *
     * @return a list of KittenImage objects
     */
    @Query("SELECT * FROM KittenImage")
    List<KittenImage> getAllKittenItem();

    /**
     * Deletes a KittenImage object from the database.
     *
     * @param k the KittenImage object to be deleted
     */
    @Delete
    void deleteKittenItem(KittenImage k);
}
