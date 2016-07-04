package plkhealth.it.app.patientbook;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.pixplicity.easyprefs.library.Prefs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class ConsultActivity extends AppCompatActivity {

    EditText txt_chat ;
    WebView mwebView;
    String list_chat_url;

    public void add_data(final String chat){

        final String url_input = Prefs.getString("api_url", "") + "frontend/web/chat/post";
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                RequestBody body = new FormBody.Builder()

                        .add("patient_cid", Prefs.getString("patient_cid", ""))
                        .add("chat_text",chat)
                        .add("doctor_or_patient","patient")
                        .add("hospcode","")
                        .build();

                Request request = new Request.Builder()
                        .url(url_input)
                        .post(body)
                        .build();

                try {
                    client.newCall(request).execute();
                    Log.d("chat post","ok");



                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
                            // change UI elements here
                            txt_chat.setText("");
                            mwebView.reload();
                        }
                    });
                }

            }
        }).start();

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("ปรึกษาหมอ");

        mwebView = (WebView)findViewById(R.id.chat_webview);
        mwebView.setBackgroundColor(Color.TRANSPARENT);
        WebSettings webSettings = mwebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mwebView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        mwebView.setScrollbarFadingEnabled(true);


        txt_chat = (EditText)findViewById(R.id.txt_chat);
        list_chat_url = Prefs.getString("api_url","")+"frontend/web/chat/list?cid="+Prefs.getString("patient_cid","");
        mwebView.loadUrl(list_chat_url);



    }
    public void btn_post_chat_click(View view){
        String mChat=txt_chat.getText().toString();
        if(!mChat.trim().equals("")){
            add_data(mChat);
        }


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
