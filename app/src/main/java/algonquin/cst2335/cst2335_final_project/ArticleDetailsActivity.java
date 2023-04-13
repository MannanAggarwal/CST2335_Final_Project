package algonquin.cst2335.cst2335_final_project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * ArticleDetailsActivity class displays the details of a selected article,
 * including its headline, URL, and publication date.
 */
public class ArticleDetailsActivity extends AppCompatActivity {
    /**
     * Constant for the intent extra key
     */
    public static final String ARTICLE_EXTRA = "article";
    /**
     * UI elements
     */
    private TextView headlineTextView;
    private TextView urlTextView;
    private TextView publicationDateTextView;

    /**
     * onCreate method is called when the activity is created.
     * Sets the view, initializes UI elements, and populates the views with
     * the details of the selected article.
     * @param savedInstanceState
     */
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);

        headlineTextView = findViewById(R.id.headline);
        urlTextView = findViewById(R.id.url);
        publicationDateTextView = findViewById(R.id.publication_date);

        Intent intent = getIntent();
        Article article = (Article) intent.getSerializableExtra(ARTICLE_EXTRA);

        if (article != null) {
            headlineTextView.setText(article.getHeadline());
            urlTextView.setText(article.getUrl());
            publicationDateTextView.setText(article.getPublicationDate());
        }
    }
}
