package plkhealth.it.app.patientbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.pixplicity.easyprefs.library.Prefs;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class ConsultActivity extends AppCompatActivity {

    EditText txt_chat ;
    //String chat;
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
                    runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
                            // change UI elements here
                            txt_chat.setText("");
                        }
                    });


                } catch (IOException e) {
                    e.printStackTrace();
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
        txt_chat = (EditText)findViewById(R.id.txt_chat);
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
}
