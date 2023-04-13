package algonquin.cst2335.cst2335_final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import algonquin.cst2335.cst2335_final_project.databinding.ActivityMainBinding;
import algonquin.cst2335.cst2335_final_project.ui.KittenHomePage;
import algonquin.cst2335.cst2335_final_project.ui.MainActivityNasa;
import algonquin.cst2335.cst2335_final_project.ui.NasaJasdeep;
import algonquin.cst2335.cst2335_final_project.ui.WeatherCurrentPage;

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

        binding.weather.setOnClickListener(clk-> {

            Intent weatherNowPage = new Intent(this, WeatherCurrentPage.class);

            startActivity(weatherNowPage);
        });

        binding.nasamarsimages.setOnClickListener(clk-> {

            Intent nasaHomePage = new Intent(this, NasaJasdeep.class);

            startActivity(nasaHomePage);
        });


    }
}