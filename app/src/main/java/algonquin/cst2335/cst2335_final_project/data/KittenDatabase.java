package algonquin.cst2335.cst2335_final_project.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(entities = {KittenImage.class}, version = 1)
public abstract class KittenDatabase extends RoomDatabase {

    public abstract KittenDAO cmDAO();


}
