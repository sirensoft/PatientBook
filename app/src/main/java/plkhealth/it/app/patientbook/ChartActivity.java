package plkhealth.it.app.patientbook;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.pixplicity.easyprefs.library.Prefs;

public class ChartActivity extends AppCompatActivity {

    WebView webView;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("กราฟ");

        webView = (WebView)findViewById(R.id.webViewChart);
        //webView.setBackgroundColor(Color.TRANSPARENT);
        if (Build.VERSION.SDK_INT >= 19) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
        else {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        //webView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
        webView.setWebViewClient(new MyWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(true);
        //webView.loadData("กรุณารอสักครู่...", "text/html; charset=utf-8", "UTF-8");
        url = Prefs.getString("api_url","")+"frontend/web/patient/chart?cid="+Prefs.getString("patient_cid","");
        Log.d("url chart",url);

        webView.loadUrl(url);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(menuItem);
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageFinished(WebView view, final String url) {
            super.onPageFinished(view, url);
        }
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view,url,favicon);
            //view.loadData("กรุณารอสักครู่...", "text/html; charset=utf-8", "UTF-8");

        }
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Log.i("WEB_VIEW_TEST", "error code:" + errorCode);
            super.onReceivedError(view, errorCode, description, failingUrl);
            view.loadData("ไม่สามารถเชื่อมโยงข้อมูลได้", "text/html; charset=utf-8", "UTF-8");

        }


    }
}
