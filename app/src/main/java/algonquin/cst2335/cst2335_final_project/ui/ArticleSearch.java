package algonquin.cst2335.cst2335_final_project.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.cst2335_final_project.R;

/**
 * ArticleSearchActivity class retrieves and displays a list of articles based on a search term.
 * Implements the ArticleAdapter.OnArticleClickListener interface to handle article click events.
 */
public class ArticleSearch extends AppCompatActivity implements ArticleHandler.OnArticleClickListener {
    /**
     * Constants for the API key and URL
     */
    private static final String API_KEY = MainActivityNewYork.API_KEY;
    private static final String API_URL = "https://api.nytimes.com/svc/search/v2/articlesearch.json?q=%s&api-key=%s";
    /**
     * UI elements
     */
    private RecyclerView recyclerView;
    private ArticleHandler articleHandler;

    /**
     * onCreate method is called when the activity is created.
     * Sets the view, initializes UI elements, sets up the RecyclerView, and
     * triggers the search for articles based on the received search term.
     *
     * @param savedInstanceState
     */
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result);

        recyclerView = findViewById(R.id.recycler_view);
        articleHandler = new ArticleHandler(this); // Pass "this" as the click listener
        recyclerView.setAdapter(articleHandler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String searchTerm = getIntent().getStringExtra(MainActivityNewYork.SEARCH_TERM_KEY);
        searchArticles(searchTerm);
    }

    /**
     * Searches for articles using the specified search term by making a request to the
     * New York Times API. Parses the response and updates the RecyclerView with the results.
     *
     * @param searchTerm The search term to use for querying articles.
     */
    private void searchArticles(String searchTerm) {
        String url = String.format(API_URL, Uri.encode(searchTerm), API_KEY); // Use Uri.encode to properly encode the search term

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray docs = response.getJSONObject("response").getJSONArray("docs");
                            List<Article> articles = new ArrayList<>();
                            for (int i = 0; i < docs.length(); i++) {
                                JSONObject doc = docs.getJSONObject(i);
                                // Parse the article data and add it to the articles list
                                String headline = doc.getJSONObject("headline").getString("main");
                                String webUrl = doc.getString("web_url");
                                String snippet = doc.getString("snippet");
                                String pubDate = doc.getString("pub_date");

                                // Assuming your Article class has a constructor like this:
                                Article article = new Article(headline, snippet, webUrl, pubDate);
                                articles.add(article);
                            }
                            // Update the RecyclerView with the new articles list
                            articleHandler.setArticles(articles);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle the error response
                        Toast.makeText(ArticleSearch.this, "Error fetching articles: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        queue.add(jsonObjectRequest);
    }

    /**
     * Handles article click events. Called when an article is clicked in the RecyclerView.
     *
     * @param position The position of the clicked article in the RecyclerView.
     */
    @Override
    public void onArticleClick(int position) {
        Article article = (Article) articleHandler.getArticles().get(position);
        Intent intent = new Intent(this, ArticlesActivity.class);

        intent.putExtra("headline", article.getHeadline());
        intent.putExtra("webUrl", article.getUrl());
        intent.putExtra("pubDate", article.getPublicationDate());

        startActivity(intent);
    }
}







