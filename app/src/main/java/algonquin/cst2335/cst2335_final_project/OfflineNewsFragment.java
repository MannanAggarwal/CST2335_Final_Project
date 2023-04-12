package algonquin.cst2335.cst2335_final_project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class OfflineNewsFragment extends Fragment {
    private ArticleAdapter mNewsAdapter;
    ListView articlesListView;

    public OfflineNewsFragment() {
        // Required empty public constructor
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_offline_news, container, false);
        articlesListView = view.findViewById(R.id.listViewArticles);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNewsAdapter = new ArticleAdapter(getActivity(), new ArrayList<NewsArticle>(), false);
        articlesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsArticle newsArticle = (NewsArticle) parent.getItemAtPosition(position);
                Intent intent = new Intent(getActivity(), NewsFeedViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("url", newsArticle.getUrl());
                bundle.putString("title", newsArticle.getTitle());
                bundle.putString("fullTitle", newsArticle.getFullTitle());
                bundle.putString("sectionTitle", newsArticle.getSectionTitle());
                bundle.putString("publishedOn", newsArticle.getPublishedOn());
                bundle.putString("mainImage", newsArticle.getMainImage());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        articlesListView.setAdapter(mNewsAdapter);
        getNewsArticles();
    }

    /**
     * This method retrieves news articles from sqlite database
     */
    private void getNewsArticles() {
        NewsArticleDatabase articleDatabase = new NewsArticleDatabase(getActivity());
        SQLiteDatabase db = articleDatabase.getReadableDatabase();

        Cursor cursor = db.query(
                NewsArticle.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        ArrayList<NewsArticle> articles = new ArrayList<>();
        while(cursor.moveToNext()) {
            String uuid = cursor.getString(cursor.getColumnIndexOrThrow(NewsArticle.UUID));
            String url = cursor.getString(cursor.getColumnIndexOrThrow(NewsArticle.URL));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(NewsArticle.TITLE));
            String fullTitle = cursor.getString(cursor.getColumnIndexOrThrow(NewsArticle.FULL_TITLE));
            String sectionTitle = cursor.getString(cursor.getColumnIndexOrThrow(NewsArticle.SECTION_TITLE));
            String publishedOn = cursor.getString(cursor.getColumnIndexOrThrow(NewsArticle.PUBLISHED_ON));
            String mainImage = cursor.getString(cursor.getColumnIndexOrThrow(NewsArticle.MAIN_IMAGE));
            NewsArticle newArticle = new NewsArticle(uuid, url, title,fullTitle,sectionTitle, publishedOn, mainImage);
            articles.add(newArticle);
        }
        mNewsAdapter.setNewsArticleList(articles);
        cursor.close();
    }
}