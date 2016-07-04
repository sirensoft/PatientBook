package plkhealth.it.app.patientbook;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class ShowNotifyActivity extends AppCompatActivity {

    WebView mwebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_notify);
        setTitle("หมอแจ้งข่าว");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mwebView = (WebView) findViewById(R.id.notify_webview);
        mwebView.setBackgroundColor(Color.TRANSPARENT);
        WebSettings webSettings = mwebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mwebView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        mwebView.setScrollbarFadingEnabled(true);

        Bundle b = getIntent().getExtras();

        Log.d("fcm data", b.getString("desc"));
        String msg = b.getString("desc");
        mwebView.loadData("<h2>&nbsp;&nbsp;" + msg + "</h2>", "text/html; charset=utf-8", "UTF-8");


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {

            finish();
        }

        return super.onOptionsItemSelected(menuItem);
    }
}
