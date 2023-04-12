/**
 * This class includes the code for basic UI components
 * A search box to enter a number and load API
 * @author Jasdeep Singh
 */

package algonquin.cst2335.cst2335_final_project;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;


public class NasaJasdeep extends AppCompatActivity {

    EditText editText;
    TextView textView;
    Button button;
    ImageButton imageButton2;

    /**
     * Method to display a alert dialog box (Bilingual)
     */
    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //setting title and message
        builder.setTitle("HELP");
        builder.setMessage(R.string.alert);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * On create function that runs when the activity loads
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.word);
        textView = findViewById(R.id.meaning);
        button = findViewById(R.id.search);
        imageButton2 = findViewById(R.id.imageButton);
        //save messages
        SharedPreferences getshared = getSharedPreferences("demo", MODE_PRIVATE);
        String value = getshared.getString("str", "Last Searched" );
        textView.setText(value);


        /**
         * Click listener for search button
         */
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getting message in string
                String msg = editText.getText().toString();
                //shared preferences to save and display last searched number
                SharedPreferences shrd = getSharedPreferences("demo", MODE_PRIVATE);
                SharedPreferences.Editor editor = shrd.edit();
                editor.putString("str", msg);
                editor.apply();
                //setting text string
                editText.setText(msg);
                //snackbar
                Snackbar snackbar = Snackbar.make(v, "Click here to load image", Snackbar.LENGTH_SHORT);
                snackbar.setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Go to another layout to get the API response for meaning
                        Intent goToProfile = new Intent(NasaJasdeep.this, MainActivity.class);
                        goToProfile.putExtra("WORD", editText.getText().toString());
                        startActivity(goToProfile);
                        snackbar.dismiss();
                    }
                });
                snackbar.show();
            }
        });

        /**
         * click listener for the info to display alert dialog box
         */
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //toast message
                Toast.makeText(NasaJasdeep.this, "Help", Toast.LENGTH_SHORT).show();
                //call to alert help box
                showAlertDialog();
            }
        });

    }
}
