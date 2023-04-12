package algonquin.cst2335.cst2335_final_project.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;


@Dao
public interface KittenDAO {



    @Insert
    void insertKittenItem(KittenImage k);


    @Query("Select * from KittenImage")
    List<KittenImage> getAllKittenItem();

    @Delete
    void deleteKittenItem(KittenImage k);





}
