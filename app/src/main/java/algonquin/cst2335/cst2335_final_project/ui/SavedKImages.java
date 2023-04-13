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

public class SavedKImages extends AppCompatActivity {
ActivityKittenImagesBinding binding;

    private RecyclerView.Adapter myAdapter;
    private ArrayList<KittenImage> kittenImages;
    private KittenDAO mDAO;
    private Executor thread;

    private KittenViewModel kittenModel;
    int position;
boolean IsPressed;
  TextView  tv_date;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
       getMenuInflater().inflate(R.menu.saved_k_images, menu);

        return true;

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);



        switch( item.getItemId() ) {
            case R.id.Item_1:

                Intent intent= new Intent(this, KittenHomePage.class);


                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
                startActivity(intent);
                this.finish();

                break;

            case R.id.Item_2:

                AlertDialog.Builder builder = new AlertDialog.Builder(SavedKImages.this);
                builder.setMessage("This application is actually getting the Kitten images of specified height and width and going through few activities in order to save and delete the image from the database.").
                        setTitle("How to use the Place Kitten Placeholder Images?").
                        setNegativeButton("ok", (dialog, cl) -> {
                        }).create().show();

                break;

            case R.id.Item_3:


                if (kittenImages.size() != 0 && IsPressed) {

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(SavedKImages.this);
                    builder1.setMessage("Do you want to Delete this Image with : " + tv_date.getText().toString()).
                            setTitle("Question").
                            setNegativeButton("No", (dialog, cl) -> {
                            })
                            .setPositiveButton("Yes", (dialog, cl) -> {

                                KittenImage removedMessage = kittenImages.get(position);
                                thread.execute(() ->
                                {

                                    mDAO.deleteKittenItem(removedMessage);

                                });
                                runOnUiThread(() -> {
                                    kittenImages.remove(position);
                                    myAdapter.notifyItemRemoved(position);
                                });


                                Snackbar.make(binding.getRoot(), "One Image deleted  " + tv_date.getText(), Snackbar.LENGTH_SHORT)
                                        .setAction("Undo", c -> {
                                            kittenImages.add(position, removedMessage);
                                            myAdapter.notifyItemInserted(position);
                                        }).show();


                                getSupportFragmentManager().popBackStack();


                                IsPressed = false;


                            }).create().show();

                }

                break;

        }
        return true;

    }

    /**
     * This is on create function.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Saved Images");

      binding=ActivityKittenImagesBinding.inflate(getLayoutInflater());
      setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        kittenModel = new ViewModelProvider(this).get(KittenViewModel.class);

        kittenImages = kittenModel.kittenItems.getValue();

        if (kittenImages == null) {

            kittenModel.kittenItems.setValue(kittenImages = new ArrayList<KittenImage>());


            thread = Executors.newSingleThreadExecutor();

            thread.execute(() ->
            {

               KittenDatabase db = Room.databaseBuilder(getApplicationContext(), KittenDatabase.class, "database-name").build();
                mDAO = db.cmDAO();


                kittenImages.addAll(mDAO.getAllKittenItem()); //Once you get the data from database


                runOnUiThread(() -> {

                    binding.recycleView.setAdapter(myAdapter);

                    setContentView(binding.getRoot());


                    if (kittenImages.size() - 1 > 0) {
                        binding.recycleView.smoothScrollToPosition(kittenImages.size() - 1);
                    }

                }); //You can then load the RecyclerView
            });

        }

        kittenModel.selectedKittenItem.observe(this, (newKittenItemValue) -> {

            Log.i("tag", "onCreate: " + "newWeatherItemValue.getName()");

            KittenDetailsFragment kittenFragment = new KittenDetailsFragment(newKittenItemValue);

            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLocation, kittenFragment).addToBackStack("").commit();

        });

        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

              KittenRowBinding kittenRowBinding = KittenRowBinding.inflate(getLayoutInflater(), parent, false);
                View root = kittenRowBinding.getRoot();
                return new MyRowHolder(root);

            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {


                holder.width.setText("width : "+ kittenImages.get(position).getWidth());
                holder.height.setText("height : "+ kittenImages.get(position).getHeight());

                holder.date.setText(kittenImages.get(position).getDate());

                holder.Image.setImageBitmap( BitmapFactory.decodeFile(kittenImages.get(position).getImagePath()));

                Log.i("s", "onBindViewHolder: "+ kittenImages.get(position).getImagePath());
            }

            @Override
            public int getItemCount() {
                return kittenImages.size();
            }

            //function to check what kind of ChatMessage object is at row position
            // If the isSend is true, then return 0
            // so that the onCreateViewHolder checks the viewType and inflates a send_message layout.
            // If isSend is false, then getItemViewType returns 1 and onCreateViewHolder checks
            // if the viewType is 1 and inflates a receive_message layout.


            @Override
            public int getItemViewType(int position) {
                return 0;
            }
        });


        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
    }


    class MyRowHolder extends RecyclerView.ViewHolder {

        public TextView width;
        public TextView height;
        public TextView date;
        public ImageView Image;

        public MyRowHolder(@NonNull View itemView) {

            super(itemView);

            itemView.setOnClickListener(clk -> {

              position= getAbsoluteAdapterPosition();
              KittenImage selected = kittenImages.get(position);

                kittenModel.selectedKittenItem.postValue(selected);
                tv_date=date;
                IsPressed=true;
            });

            Image = itemView.findViewById(R.id.kitten_row_image);
            width= itemView.findViewById(R.id.kitten_row_width);
            height = itemView.findViewById(R.id.kitten_row_height);
            date = itemView.findViewById(R.id.kitten_row_date);

        }
    }
}