package algonquin.cst2335.cst2335_final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import algonquin.cst2335.cst2335_final_project.databinding.ActivityMainBinding;
import algonquin.cst2335.cst2335_final_project.ui.KittenHomePage;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.kitten.setOnClickListener(c->{
            Intent placeKittenPage = new Intent(this, KittenHomePage.class);

            startActivity(placeKittenPage);



        });



    }
}