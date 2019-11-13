package siergo_o.onlinernews;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class DescriptionWebView extends AppCompatActivity {

    String Url;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description_web_view);

        WebView webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);

        Bundle extras = getIntent().getExtras();


        if (extras != null) {
            Url = extras.getString("Url");
            // and get whatever type user account id is
        }
        webView.loadUrl(Url);
    }
}
