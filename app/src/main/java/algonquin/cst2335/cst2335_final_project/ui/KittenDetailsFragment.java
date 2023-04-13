/**
 * @author Mannan
 */
package algonquin.cst2335.cst2335_final_project.ui;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import algonquin.cst2335.cst2335_final_project.data.KittenImage;
import algonquin.cst2335.cst2335_final_project.databinding.KittenDetailsBinding;

/**
 * A fragment that displays details of a selected KittenImage.
 */
public class KittenDetailsFragment extends Fragment {

    /** The selected KittenImage to display. */
    KittenImage selected;

    /**
     * Constructs a new KittenDetailsFragment.
     *
     * @param k The selected KittenImage to display.
     */
    public KittenDetailsFragment(KittenImage k) {
        selected = k;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        return true;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        // Inflate the layout for this fragment using the view binding.
        KittenDetailsBinding binding = KittenDetailsBinding.inflate(inflater);

        // Set the background color to white.
        binding.getRoot().setBackgroundColor(Color.WHITE);

        // Set the text for each detail.
        binding.kittenDetailsId.setText("Id = " + selected.id);
        binding.kittenDetailsWidth.setText("width : " + selected.getWidth());
        binding.kittenDetailsHeight.setText("height : " + selected.getHeight());
        binding.kittenDetailsDate.setText("Date : " + selected.getDate());

        // Set the image bitmap from the selected KittenImage's image path.
        binding.weatherDetailsIcon.setImageBitmap(BitmapFactory.decodeFile(selected.getImagePath()));

        // Return the root view of the inflated layout.
        return binding.getRoot();
    }
}
