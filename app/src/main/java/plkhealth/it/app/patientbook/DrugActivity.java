package plkhealth.it.app.patientbook;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.provider.AlarmClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pixplicity.easyprefs.library.Prefs;

public class DrugActivity extends AppCompatActivity {


    WebView mwebView;
    View view_drug;

    private void loadNewData(String url) {
        // Request a string response
        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Prefs.putString("patient_drug", response);
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
        RetryPolicy policy = new DefaultRetryPolicy(30000//milli-sec
                , DefaultRetryPolicy.DEFAULT_MAX_RETRIES
                , DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        Volley.newRequestQueue(this).add(stringRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("ยา");
        view_drug = (View)findViewById(R.id.layout_drug) ;

        final String url_drug = Prefs.getString("api_url","")+"frontend/web/index.php/patient/drug?cid="+Prefs.getString("patient_cid","");


        mwebView = (WebView)findViewById(R.id.web_drug);
        mwebView.setBackgroundColor(Color.TRANSPARENT);
        WebSettings webSettings = mwebView.getSettings();
        webSettings.setJavaScriptEnabled(true);


        mwebView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        mwebView.setScrollbarFadingEnabled(true);


        mwebView.loadData(Prefs.getString("patient_drug",""),"text/html; charset=utf-8", "UTF-8");

        //mwebView.setWebViewClient(new MyWebViewClient());


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_drug);
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
                            loadNewData(url_drug);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }).start();

            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_drug, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }

        if (menuItem.getItemId() == R.id.action_set_drug_time) {
            Intent i = new Intent(AlarmClock.ACTION_SET_ALARM);
            //i.putExtra(AlarmClock.EXTRA_HOUR, 9);
            //i.putExtra(AlarmClock.EXTRA_MINUTES, 37);
            i.putExtra(AlarmClock.EXTRA_MESSAGE,"ได้เวลาทานยา...");
           
            startActivity(i);

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
