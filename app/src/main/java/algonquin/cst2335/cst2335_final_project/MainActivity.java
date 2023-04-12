package algonquin.cst2335.cst2335_final_project;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import algonquin.cst2335.cst2335_final_project.UI.WeatherCurrentPage;
import algonquin.cst2335.cst2335_final_project.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        binding.weather.setOnClickListener(clk->{

            Intent weatherNowPage=new Intent(this, WeatherCurrentPage.class);

            startActivity(weatherNowPage);
        });



    }
}