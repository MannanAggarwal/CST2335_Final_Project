package algonquin.cst2335.cst2335_final_project.data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class WeatherViewModel extends ViewModel {

    public MutableLiveData<ArrayList<WeatherItem>> weatherItems = new MutableLiveData<>();

    public MutableLiveData<WeatherItem> selectedWeatherItem = new MutableLiveData< >();





}
