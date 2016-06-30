package plkhealth.it.app.patientbook;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pixplicity.easyprefs.library.Prefs;

public class RiskActivity extends AppCompatActivity {

    WebView mwebView;
    View view_risk;

    private void loadNewData(String url) {
        // Request a string response
        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Prefs.putString("patient_risk", response);
                        //String patient_risk = Prefs.getString("patient_risk", "");
                        mwebView.loadData(response, "text/html; charset=utf-8", "UTF-8");

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Error handling
                mwebView.loadData("ไม่สามารถเชื่อมต่อข้อมูลได้..", "text/html; charset=utf-8", "UTF-8");
                error.printStackTrace();

            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risk);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("สถานะสุขภาพ/ความเสี่ยง");
        view_risk = (View)findViewById(R.id.layout_risk) ;

        final String url_risk = Prefs.getString("api_url","")+"frontend/web/patient/risk?cid="+Prefs.getString("patient_cid","");


        mwebView = (WebView)findViewById(R.id.web_risk);
        mwebView.setBackgroundColor(Color.TRANSPARENT);
        WebSettings webSettings = mwebView.getSettings();
        webSettings.setJavaScriptEnabled(true);


        mwebView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        mwebView.setScrollbarFadingEnabled(true);


        mwebView.loadData(Prefs.getString("patient_risk",""),"text/html; charset=utf-8", "UTF-8");

        //mwebView.setWebViewClient(new MyWebViewClient());


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_risk);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mwebView.loadData("กรุณารอสักครู่...", "text/html; charset=utf-8", "UTF-8");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // do the thing that takes a long time
                        try {

                            Thread.sleep(3000);
                           loadNewData(url_risk);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }).start();

            }
        });



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
