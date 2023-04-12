package algonquin.cst2335.cst2335_final_project.Data;

import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(entities = {WeatherItem.class}, version = 1)
public abstract class WeatherDatabase extends RoomDatabase {

    public abstract WeatherItemDAO cmDAO();


}
