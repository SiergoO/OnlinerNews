package siergo_o.onlinernews.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;
import kotlinx.android.synthetic.main.description_web_view.*

import siergo_o.onlinernews.R;

class DescriptionWebView : AppCompatActivity() {

    private var url: String? = ""

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.description_web_view)
        val webView: WebView = findViewById(R.id.webView)
        webView.settings.javaScriptEnabled = true
        val extras = intent.extras
        if (extras != null) {
            url = extras.getString("Url")
        }
        webView.loadUrl(url)
    }
}
