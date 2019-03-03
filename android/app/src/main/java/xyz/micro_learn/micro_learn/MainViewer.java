package xyz.micro_learn.micro_learn;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.webkit.WebView;
import android.content.Intent;

import static android.Manifest.permission.INTERNET;

public class MainViewer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_viewer);


        final WebView webDisplay = (WebView) findViewById(R.id.WebView);
        webDisplay.setWebViewClient(new WebClient());
        webDisplay.loadUrl("https://www.wikipedia.org/");


        Button backButton = (Button) findViewById(R.id.Back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                webDisplay.goBack();

            }
        });

        Button logoutButton = (Button) findViewById(R.id.Logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainViewer.this, LoginActivity.class));

            }
        });
    }
}
