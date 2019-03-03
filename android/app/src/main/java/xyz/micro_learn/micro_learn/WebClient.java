package xyz.micro_learn.micro_learn;
import android.webkit.WebView;
import android.webkit.WebViewClient;
public class WebClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }
}