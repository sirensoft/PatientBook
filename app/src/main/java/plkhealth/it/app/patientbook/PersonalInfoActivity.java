package plkhealth.it.app.patientbook;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


public class PersonalInfoActivity extends AppCompatActivity {


    MyGlobals myGlobal;
    WebView webView;
    ImageView img_personal_info;


    private void bindWebView() {
        webView = (WebView) findViewById(R.id.wb_personal_info);
        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
        webView.setWebViewClient(new MyWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);

    }
    private void bindWidget(){
        img_personal_info = (ImageView)findViewById(R.id.img_personal_info);
    }

    private void loadNewData(String url) {
        // Request a string response
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        Prefs.putString("personal_info", response);


                        String personal_info = Prefs.getString("personal_info", "");

                        webView.loadData(personal_info, "text/html; charset=utf-8", "UTF-8");



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // Error handling
                webView.loadData("ไม่สามารถเชื่อมต่อข้อมูลได้", "text/html; charset=utf-8", "UTF-8");
                error.printStackTrace();

            }
        });

// Add the request to the queue
        Volley.newRequestQueue(this).add(stringRequest);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("ข้อมูลส่วนตัว");

        myGlobal = new MyGlobals(getApplicationContext());

        String patient_cid = myGlobal.getPatientCid();
        String ApiUrl = myGlobal.getApiUrl();

        bindWebView();
        bindWidget();

        webView.loadData(Prefs.getString("personal_info", ""), "text/html; charset=utf-8", "UTF-8");
        final String url = ApiUrl + "/frontend/web/index.php?r=patient/index&cid=" + patient_cid;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_personal_info);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.loadData("กรุณารอสักครู่...", "text/html; charset=utf-8", "UTF-8");
                loadNewData(url);
                Glide.with(getApplicationContext()).load("https://cdn4.iconfinder.com/data/icons/LUMINA/web_design/png/400/members.png").into(img_personal_info);

            }
        });

        Glide.with(getApplicationContext()).load("https://cdn4.iconfinder.com/data/icons/LUMINA/web_design/png/400/members.png").into(img_personal_info);





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
    }


}
