// The following code is for the KittenViewModel class, which is responsible for storing and managing kitten data in the application.
/**
 * @author Mannan
 */
package algonquin.cst2335.cst2335_final_project.data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class KittenViewModel extends ViewModel {
    // A MutableLiveData object that stores an ArrayList of KittenImage objects
    public MutableLiveData<ArrayList<KittenImage>> kittenItems = new MutableLiveData<>();

    // A MutableLiveData object that stores the selected KittenImage object
    public MutableLiveData<KittenImage> selectedKittenItem = new MutableLiveData<>();
}
