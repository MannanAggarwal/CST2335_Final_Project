package algonquin.cst2335.cst2335_final_project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class NewsFeedViewActivity extends AppCompatActivity {
    TextView fullTitleTv;
    Toolbar toolbar;
    ImageView mainImageView;
    TextView sectionTitleTV;
    TextView publishDateTv;
    TextView urlTv;
    Button saveButton;

    @Override
    public void setSupportActionBar(@Nullable androidx.appcompat.widget.Toolbar toolbar) {
        super.setSupportActionBar(toolbar);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed_view);
        fullTitleTv = findViewById(R.id.fullTitleTv);
        mainImageView = findViewById(R.id.mainImageView);
        sectionTitleTV = findViewById(R.id.sectionTitleTV);
        publishDateTv = findViewById(R.id.publishDateTv);
        urlTv = findViewById(R.id.urlTv);
        saveButton = findViewById(R.id.saveButton);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        String url = "https://api.nytimes.com/svc/search/v2/articlesearch.json?q=HiZcuAlDU1DjHxJ2&api-key=wsGlRLJsQX1OMZ8SJ2oMMchwoHQZ9tfa";
        String title;
        String fullTitle;
        String sectionTitle;
        String publishedOn;
        String mainImage;
        DownLoadImageAsyncTask imageAsyncTask = new DownLoadImageAsyncTask();
        if (bundle != null) {
            mainImage = bundle.getString("mainImage");
            imageAsyncTask.execute(mainImage);
            url = bundle.getString("url");
            urlTv.setText(url);
            title = bundle.getString("title");
            fullTitle = bundle.getString("fullTitle");
            fullTitleTv.setText(fullTitle);
            sectionTitle = bundle.getString("sectionTitle");
            sectionTitleTV.setText("Section: " + sectionTitle);
            publishedOn = bundle.getString("publishedOn");
            publishDateTv.setText("Published On: " + publishedOn);
        }
        final String finalUrl = url;
        urlTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewsFeedViewActivity.this,Articles.class);
                Bundle bundle = new Bundle();
                bundle.putString("url", finalUrl);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public class DownLoadImageAsyncTask extends AsyncTask<String,Void,Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {
            URL url = null;
            Bitmap bmp;
            try {
                url = new URL(strings[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                return bmp;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            mainImageView.setImageBitmap(bitmap);
        }
    }
}
