package algonquin.cst2335.cst2335_final_project.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;


@Dao
public interface WeatherItemDAO {



    @Insert
    void insertMessage(WeatherItem w);


    @Query("Select * from WeatherItem")
    List<WeatherItem> getAllWeatherItems();

    @Delete
    void deleteWeatherItem(WeatherItem w);





}
