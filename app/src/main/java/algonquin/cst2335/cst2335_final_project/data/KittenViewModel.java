package algonquin.cst2335.cst2335_final_project.data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class KittenViewModel extends ViewModel {

    public MutableLiveData<ArrayList<KittenImage>>kittenItems = new MutableLiveData<>();

    public MutableLiveData<KittenImage> selectedKittenItem = new MutableLiveData< >();





}
