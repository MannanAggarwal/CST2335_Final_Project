/**
 * Class to display image URL and other attributes using volley server and API of NASA
 * @author Jasdeep Singh
 */

package algonquin.cst2335.cst2335_final_project.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import algonquin.cst2335.cst2335_final_project.R;

/**
 * Main activity to implement the logic
 */
public class MainActivityNasa extends AppCompatActivity {
    private static final String API_KEY = "s7ocexsZHz3Tf129R1krhRb6GgdVQLuLFRD5dxgw";
    private String word;
    private ImageView imageView;
    private TextView textView;
    private TextView textView2;
    private Button back;
    private Button load;
    String imageUrl;
    String date;
    Fragment newFrag = new Fragment();


    Handler mainHandler = new Handler();
    ProgressDialog progressDialog;


    /**
     * On create method to load UI elements when activity starts
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        Intent fromMain = getIntent();
        word = fromMain.getStringExtra("WORD");
        back = findViewById(R.id.back);
        imageView = findViewById(R.id.image);
        load = findViewById(R.id.load);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        //textView.setText(loadImageFromApi());


        /**
         * click listener for the back button to load the previous page
         */
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToProfile = new Intent(MainActivityNasa.this, NasaJasdeep.class);
                startActivity(goToProfile);
            }
        });

        /**
         * click listener for the load button to load the image using the defined methods
         */
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = loadImageFromApi();
                //Log.w("Jassi", url);
                new FetchImage(url).start();
                Picasso.get().load(newFrag.display()).into(imageView);
            }
        });
    }

    /**
     * Method to load the API URL and give back the required data in form of JSON Object using Volley
     * @return
     */
    private String loadImageFromApi() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=" + word + "&api_key=" + API_KEY;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    /**
                     * Logic to implement as response from API
                     * @param response
                     */
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Parse the JSON response and retrieve the first image URL.
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray photosArray = jsonObject.getJSONArray("photos");
                            imageUrl = photosArray.getJSONObject(0).getString("img_src");
                            date = photosArray.getJSONObject(0).getString("earth_date");
                            textView.setText(imageUrl);
                            textView2.setText(date);
                            // Use Picasso to load the image into the ImageView.
                            //Picasso.get().load(imageUrl).into(imageView);
                            Toast.makeText(MainActivityNasa.this, "Done", Toast.LENGTH_SHORT).show();

                            Log.w("url", imageUrl);
                            Log.w("date", date);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivityNasa.this, "Error getting image URL", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {


            /**
             * Logic to implement as response if error occurs
             * @param error
             */
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivityNasa.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
        return imageUrl;
    }

    /**
     * Class to fetch the image based upon url as the response from the API
     */
    class FetchImage extends Thread {

        String URL;
        Bitmap bitmap;

        FetchImage(String URL) {

            this.URL = URL;

        }

        @Override
        public void run() {

            mainHandler.post(new Runnable() {
                @Override
                public void run() {

                    progressDialog = new ProgressDialog(MainActivityNasa.this);
                    progressDialog.setMessage("Getting your pic....");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                }
            });

            InputStream inputStream = null;
            try {
                inputStream = new URL(URL).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }

            mainHandler.post(new Runnable() {
                @Override
                public void run() {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    imageView.setImageBitmap(bitmap);
                }
            });
        }
    }
}