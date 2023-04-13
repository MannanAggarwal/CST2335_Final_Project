/**
 * This activity allows the user to retrieve and save images of kittens of a specified size.
 * The images are retrieved from the place kitten.com website using Volley, and saved to a local file
 * so they can be displayed again without needing to be re-downloaded. The user can also save images
 * to a Room database to view them later. The user can access the saved images using the navigation menu.
 */
package algonquin.cst2335.cst2335_final_project.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.cst2335_final_project.R;
import algonquin.cst2335.cst2335_final_project.data.KittenDAO;
import algonquin.cst2335.cst2335_final_project.data.KittenDatabase;
import algonquin.cst2335.cst2335_final_project.data.KittenImage;
import algonquin.cst2335.cst2335_final_project.databinding.ActivityGetKittenImageBinding;

public class KittenHomePage extends AppCompatActivity {
    ActivityGetKittenImageBinding binding;

    SharedPreferences prefs; // Shared preferences object to store user preferences
    private Executor thread; // Executor for running database operations in a separate thread

    private KittenDAO mDAO; // Data access object for the kitten image database
    String imagePath; // File path of the saved kitten image
    Bitmap image; // Bitmap object for the kitten image

    // Request queue for Volley
    protected RequestQueue queue = null;

    /**
     * Inflate the menu layout for this activity.
     *
     * @param menu The menu to inflate.
     * @return True if the menu was inflated successfully.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.kitten_home_page, menu);
        return true;
    }

    /**
     * Handle a menu item being selected.
     *
     * @param item The menu item that was selected.
     * @return True if the menu item was handled successfully.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.Item_1:
                // Open the SavedKImages activity to display saved images
                Intent intent = new Intent(this, SavedKImages.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
                startActivity(intent);
                this.finish();
                break;
            case R.id.Item_2:
                // Display an informational dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(KittenHomePage.this);
                builder.setMessage("This application is actually getting the Kitten images of specified height and width and going through few activities in order to save and delete the image from the database.").
                        setTitle("How to use the Place Kitten Placeholder Images?").
                        setNegativeButton("ok", (dialog, cl) -> {
                        }).create().show();
                break;
            case R.id.Item_3:
                // Save the current kitten image to the database
                thread.execute(() -> {
                    // If an image has been retrieved, insert it into the database
                    if (image != null) {
                        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
                        String currentDatEndTime = sdf.format(new Date());
                        mDAO.insertKittenItem(new KittenImage(binding.width.getText().toString(), binding.height.getText().toString(), currentDatEndTime, imagePath));
                        Snackbar.make(binding.getRoot(), "Images added to favourites.", Snackbar.LENGTH_SHORT).show();
                    }
                });
                break;
        }
        return true;
    }

    /**
     * Called when the activity is first created. Initializes the UI elements, database, and Volley request queue.
     *
     * @param savedInstanceState A Bundle containing the saved state of the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Kitten Placeholder");

        // Initialize the view binding object
        binding = ActivityGetKittenImageBinding.inflate(getLayoutInflater());

        // Set the toolbar as the action bar for the activity
        setSupportActionBar(binding.toolbar);

        // Initialize the Volley request queue
        queue = Volley.newRequestQueue(this);

        // Initialize the shared preferences object
        prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        // Set the height text field with the value retrieved from SharedPreferences
        binding.width.setText(prefs.getString("width", ""));
        binding.height.setText(prefs.getString("height", ""));

// Create a new single-threaded ExecutorService to run database operations on a separate thread
        thread = Executors.newSingleThreadExecutor();
        thread.execute(() -> {
            // Build an instance of the KittenDatabase Room database
            KittenDatabase db = Room.databaseBuilder(getApplicationContext(), KittenDatabase.class, "database-name").build();
            // Initialize the DAO for communication with the database
            mDAO = db.cmDAO();
        });

// Switch to the main UI thread and set the content view to the root view of the binding
        runOnUiThread(() -> {
            setContentView(binding.getRoot());
        });

// Set a click listener on the "Get Image" button
        binding.btnGetImage.setOnClickListener(c -> {

            // Show a toast to indicate that an image is being added
            Toast.makeText(this, "Adding Image!", Toast.LENGTH_SHORT).show();

            // Get the values of width and height from the input fields
            String width = binding.width.getText().toString();
            String height = binding.height.getText().toString();

            // Create a filename based on the width and height values
            String imageName = width + height + ".png";

            // Create an image URL based on the width and height values
            String imgUrl = "https://placekitten.com/" + width + "/" + height;

            // Set the image path to the application's files directory with the filename
            imagePath = getFilesDir() + "/" + imageName;
            File file = new File(imagePath);

            // Check if the image file exists
            if (file.exists()) {
                // If it exists, decode the file into a Bitmap object
                image = BitmapFactory.decodeFile(imagePath);
            } else {
                // If it does not exist, create a new ImageRequest object to retrieve the image
                ImageRequest imgReq = new ImageRequest(imgUrl, new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        // When the image is retrieved, save it to a file and decode it into a Bitmap object
                        image = bitmap;
                        FileOutputStream fOut = null;
                        try {
                            fOut = openFileOutput(imageName, Context.MODE_PRIVATE);
                            image.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                            fOut.flush();
                            fOut.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }, 1024, 1024, ImageView.ScaleType.CENTER, null, (error) -> {
                });

                // Add the ImageRequest to a request queue to retrieve the image
                queue.add(imgReq);
            }

            // Switch back to the main UI thread and set the kitten image to the retrieved image
            runOnUiThread(() -> {
                binding.kittenImage.setImageBitmap(image);
            });
        });
    }}