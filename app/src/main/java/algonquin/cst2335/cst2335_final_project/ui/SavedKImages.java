package algonquin.cst2335.cst2335_final_project.ui;


import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import algonquin.cst2335.cst2335_final_project.data.KittenDAO;
import algonquin.cst2335.cst2335_final_project.data.KittenDatabase;
import algonquin.cst2335.cst2335_final_project.data.KittenImage;
import algonquin.cst2335.cst2335_final_project.data.KittenViewModel;
import algonquin.cst2335.cst2335_final_project.R;
import algonquin.cst2335.cst2335_final_project.databinding.ActivityKittenImagesBinding;
import algonquin.cst2335.cst2335_final_project.databinding.KittenRowBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
/**
 *  A class to handle the activity for displaying and managing saved Kitten images.
 */
public class SavedKImages extends AppCompatActivity {
    // View binding instance
    ActivityKittenImagesBinding binding;

    // Adapter for the RecyclerView
    private RecyclerView.Adapter myAdapter;

    // List of KittenImages
    private ArrayList<KittenImage> kittenImages;

    // Data Access Object for Kitten Images
    private KittenDAO mDAO;

    // Executor to handle database operations in a separate thread
    private Executor thread;

    // ViewModel to handle communication between fragments
    private KittenViewModel kittenModel;

    // Current position of selected item
    int position;

    // Flag to keep track of whether an item is selected
    boolean IsPressed;

    // TextView for displaying the date of the selected image
    TextView tv_date;

    /**
     * Creates the options menu for the activity.
     *
     * @param menu The menu to create.
     * @return Whether or not the menu was created successfully.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.saved_k_images, menu);

        return true;
/**
 *  Handles selection of an item from the options menu.
 *  @param item The selected menu item.
 *  @return Whether or not the item was handled successfully.
 */
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);


        switch (item.getItemId()) {
            // Handle selection of the "Home" item in the menu
            case R.id.Item_1:
                Intent intent = new Intent(this, KittenHomePage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
                startActivity(intent);
                this.finish();
                break;

            // Handle selection of the "How to Use" item in the menu
            case R.id.Item_2:
                AlertDialog.Builder builder = new AlertDialog.Builder(SavedKImages.this);
                builder.setMessage("This application is actually getting the Kitten images of specified height and width and going through few activities in order to save and delete the image from the database.").
                        setTitle("How to use the Place Kitten Placeholder Images?").
                        setNegativeButton("ok", (dialog, cl) -> {
                        }).create().show();
                break;

            // Handle selection of the "Delete" item in the menu
            case R.id.Item_3:
                if (kittenImages.size() != 0 && IsPressed) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(SavedKImages.this);
                    builder1.setMessage("Do you want to Delete this Image with : " + tv_date.getText().toString()).
                            setTitle("Question").
                            setNegativeButton("No", (dialog, cl) -> {
                            })
                            .setPositiveButton("Yes", (dialog, cl) -> {
                                // Remove item from database in separate thread
                                KittenImage removedMessage = kittenImages.get(position);
                                thread.execute(() -> {
                                    mDAO.deleteKittenItem(removedMessage);
                                });
                                // Remove item from RecyclerView
                                runOnUiThread(() -> {
                                    kittenImages.remove(position);
                                    myAdapter.notifyItemRemoved(position);
                                });
                                // Show Snackbar with option to undo delete
                                Snackbar.make(binding.getRoot(), "One Image deleted  " + tv_date.getText(), Snackbar.LENGTH_SHORT)
                                        .setAction("Undo", c -> {
                                            kittenImages.add(position, removedMessage);
                                            myAdapter.notifyItemInserted(position);
                                        }).show();
                                // Return to previous fragment and reset flag
                                getSupportFragmentManager().popBackStack();
                                IsPressed = false;
                            }).create().show();
                }
                break;
        }
        return true;

    }

    /**
     * This method is called when the activity is created. It sets up the views and initializes the kittenModel,
     * which is a ViewModel class for managing kitten images. If there are no saved images in the database, it loads
     * them from the database using a separate thread and displays them in the RecyclerView. When a kitten image is
     * clicked, it launches a new fragment that displays the details of the selected kitten image.
     *
     * @param savedInstanceState The saved instance state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Saved Images");

        // Inflate the layout using the data binding
        binding = ActivityKittenImagesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        // Initialize the kitten model
        kittenModel = new ViewModelProvider(this).get(KittenViewModel.class);

        // Get the saved kitten images from the kittenModel
        kittenImages = kittenModel.kittenItems.getValue();

        // If there are no saved kitten images, load them from the database using a separate thread
        if (kittenImages == null) {
            kittenModel.kittenItems.setValue(kittenImages = new ArrayList<KittenImage>());

            thread = Executors.newSingleThreadExecutor();
            thread.execute(() -> {
                // Load the kitten images from the database
                KittenDatabase db = Room.databaseBuilder(getApplicationContext(), KittenDatabase.class, "database-name").build();
                mDAO = db.cmDAO();
                kittenImages.addAll(mDAO.getAllKittenItem());

                // Update the UI on the main thread
                runOnUiThread(() -> {
                    binding.recycleView.setAdapter(myAdapter);

                    setContentView(binding.getRoot());

                    if (kittenImages.size() - 1 > 0) {
                        binding.recycleView.smoothScrollToPosition(kittenImages.size() - 1);
                    }
                });
            });
        }

        // Listen for changes in the selected kitten item in the kittenModel
        kittenModel.selectedKittenItem.observe(this, (newKittenItemValue) -> {
            // Launch a new fragment that displays the details of the selected kitten image
            KittenDetailsFragment kittenFragment = new KittenDetailsFragment(newKittenItemValue);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLocation, kittenFragment).addToBackStack("").commit();
        });

        // Set up the RecyclerView to display the saved kitten images
        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                // Inflate the kitten row layout using the data binding and return a new MyRowHolder
                KittenRowBinding kittenRowBinding = KittenRowBinding.inflate(getLayoutInflater(), parent, false);
                View root = kittenRowBinding.getRoot();
                return new MyRowHolder(root);
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                // Bind the kitten image data to the views in the MyRowHolder
                holder.width.setText("width : " + kittenImages.get(position).getWidth());
                holder.height.setText("height : " + kittenImages.get(position).getHeight());
                holder.date.setText(kittenImages.get(position).getDate());
                holder.Image.setImageBitmap(BitmapFactory.decodeFile(kittenImages.get(position).getImagePath()));
            }

            @Override
            public int getItemCount() {
                return kittenImages.size();
            }

            @Override
            public int getItemViewType(int position) {
                return 0;
            }
        });

        // Set the RecyclerView to use a linear layout manager
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * ViewHolder class for a row in a RecyclerView displaying information about a kitten.
     */
    class MyRowHolder extends RecyclerView.ViewHolder {

        /**
         * TextView displaying the width of the kitten image.
         */
        public TextView width;

        /**
         * TextView displaying the height of the kitten image.
         */
        public TextView height;

        /**
         * TextView displaying the date the kitten image was uploaded.
         */
        public TextView date;

        /**
         * ImageView displaying the kitten image.
         */
        public ImageView Image;

        /**
         * Constructor for the MyRowHolder class.
         *
         * @param itemView The view representing a single row in the RecyclerView.
         */
        public MyRowHolder(@NonNull View itemView) {

            super(itemView);

            // Set an OnClickListener on the row to handle when it's clicked.
            itemView.setOnClickListener(clk -> {

                // Get the absolute adapter position of the clicked row.
                position = getAbsoluteAdapterPosition();

                // Get the KittenImage object representing the clicked row.
                KittenImage selected = kittenImages.get(position);

                // Set the selected kitten item in the ViewModel.
                kittenModel.selectedKittenItem.postValue(selected);

                // Update the date TextView to the value of the clicked row.
                tv_date = date;

                // Set a flag indicating that the row has been clicked.
                IsPressed = true;
            });

            // Initialize the Image, width, height, and date TextViews with their corresponding Views in the row.
            Image = itemView.findViewById(R.id.kitten_row_image);
            width = itemView.findViewById(R.id.kitten_row_width);
            height = itemView.findViewById(R.id.kitten_row_height);
            date = itemView.findViewById(R.id.kitten_row_date);
        }
    }
}