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
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.pixplicity.easyprefs.library.Prefs;

public class ChartActivity extends AppCompatActivity {

    WebView mwebView;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("กราฟ");

        url = Prefs.getString("api_url","")+"frontend/web/patient/chart?cid="+Prefs.getString("patient_cid","");

        mwebView = (WebView)findViewById(R.id.webViewChart);
        mwebView.setBackgroundColor(Color.TRANSPARENT);
        WebSettings webSettings = mwebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //improve webView performance
        if (Build.VERSION.SDK_INT >= 19) {
            mwebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
        else {
            mwebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        mwebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        //mwebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        mwebView.getSettings().setAppCacheEnabled(true);
        mwebView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        mwebView.setScrollbarFadingEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        //webSettings.setUseWideViewPort(true);
        //webSettings.setSavePassword(true);
        //webSettings.setSaveFormData(true);
        webSettings.setEnableSmoothTransition(true);

        mwebView.loadUrl(url);
        Log.d("url chart",url);

        mwebView.setWebViewClient(new MyWebViewClient());

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
            view.loadData("ไม่สามารถเชื่อมโยงข้อมูลได้", "text/html; charset=utf-8", "UTF-8");
            super.onReceivedError(view, errorCode, description, failingUrl);


        }


    }
}
