package algonquin.cst2335.cst2335_final_project;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class Articles extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView myWebView = new WebView(this);
        setContentView(myWebView);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            String url = bundle.getString("url");
            myWebView.loadUrl(url);
        }
    }
}
