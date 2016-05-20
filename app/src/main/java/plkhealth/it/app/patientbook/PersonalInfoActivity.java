package plkhealth.it.app.patientbook;

import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class PersonalInfoActivity extends AppCompatActivity {


    MyGlobals myGlobal;
    WebView webView ;

    private void bindWidget(){
        webView = (WebView) findViewById(R.id.wb_personal_info);
        webView.setWebViewClient(new MyWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("ข้อมูลส่วนตัว");
        myGlobal = new MyGlobals(getApplicationContext());

        //pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //String patient_cid = pref.getString("patient_cid", "");
        String patient_cid =myGlobal.getPatientCid();
        //Toast.makeText(getApplicationContext(),patient_cid,Toast.LENGTH_SHORT).show();

        bindWidget();
        webView.loadData(patient_cid, "text/html", "UTF-8");
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
