package algonquin.cst2335.cst2335_final_project;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import androidx.appcompat.widget.Toolbar;

public class NewsFeedActivity extends AppCompatActivity {

    MenuItem actionProgressItem;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtivity_news_feed);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("News Feed");
        loadFragment(new OnlineNewsFragment(), "");
    }

    private void loadFragment(Fragment fragment, String tag) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment, tag);
        //transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news_articles, menu);
        return true;
    }

    public void showHideProgressBar(boolean show) {
        actionProgressItem.setVisible(show);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Store instance of the menu item containing progress
        actionProgressItem = menu.findItem(R.id.actionProgress);
        // Extract the action-view from the menu item
        ProgressBar v = (ProgressBar) actionProgressItem.getActionView();

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.item_read_online:
                loadFragment(new OnlineNewsFragment(), "");
                return true;
            case R.id.item_read_offline:
                loadFragment(new OfflineNewsFragment(), "");
                return true;
            case R.id.item_help:
                NYTArticles dialogHelp = new NYTArticles(this, true, new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        dialog.dismiss();
                    }
                });
                dialogHelp.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
