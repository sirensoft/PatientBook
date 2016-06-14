package plkhealth.it.app.patientbook;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.pixplicity.easyprefs.library.Prefs;
import com.readystatesoftware.viewbadger.BadgeView;


import com.loopj.android.http.*;

import java.io.IOException;

import cz.msebera.android.httpclient.Header;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


public class MainActivity extends AppCompatActivity {

    ProgressDialog progress;

    MyGlobals myGlobal;


    public void check_active(String url) {

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("volley",response);
                        if (response.equals("1")) {
                            Prefs.putString("is_active", "1");

                        }else{
                            Prefs.putString("is_active", "0");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        });

        Volley.newRequestQueue(this).add(stringRequest);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final View viewParent = findViewById(R.id.myCoordinatorLayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.apps1);




        // set token
        final String firebase_token = FirebaseInstanceId.getInstance().getToken();
        Prefs.putString("token", firebase_token);
        Log.d("token", firebase_token);
        final String url_update_token = Prefs.getString("api_url", "") + "frontend/web/media/update-token";
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                RequestBody body = new FormBody.Builder()
                        .add("token", firebase_token)
                        .add("cid", Prefs.getString("patient_cid", ""))
                        .build();

                Request request = new Request.Builder()
                        .url(url_update_token)
                        .post(body)
                        .build();

                try {
                    client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
        // จบ token


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!Prefs.getString("is_active", "").equals("1")) {
                    Snackbar snackbar = Snackbar.make(viewParent, "ไม่ได้รับอนุญาต", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    return;
                }
                progress = ProgressDialog.show(MainActivity.this, "โปรดรอสักครู่",
                        "เรียกข้อมูลใหม่...", true);
                progress.setCancelable(false);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // do the thing that takes a long time
                        try {
                            Thread.sleep(5000);
                        } catch (Exception e) {

                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                View btn_appointment = findViewById(R.id.btn_appointment);
                                BadgeView badge1 = new BadgeView(getApplicationContext(), btn_appointment);
                                badge1.setText("N");
                                badge1.show();

                                View btn_promotion = findViewById(R.id.btn_promotion);
                                BadgeView badge2 = new BadgeView(getApplicationContext(), btn_promotion);
                                badge2.setText("N");
                                badge2.show();


                                progress.dismiss();

                            }
                        });
                    }
                }).start();

            }
        });

        myGlobal = new MyGlobals(getApplicationContext());
        myGlobal.setBadge(getApplicationContext(), 0);


    }

    @Override
    public void onResume(){
        super.onResume();
        String url_check_active = Prefs.getString("api_url", "") + "frontend/web/patient/check-active?cid=" + Prefs.getString("patient_cid", "");
        check_active(url_check_active);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, AdminLoginActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void btnPersonalInfo_Click(View view) {
        Intent intent = new Intent(this, PersonalInfoActivity.class);
        startActivity(intent);
    }

    public void btnDoctorVisit_Click(View view) {
        Intent intent = new Intent(this, DoctorLoginActivity.class);
        startActivity(intent);
    }

    public void btnHospital_Click(View view) {
        Intent intent = new Intent(this, HospitalActivity.class);
        startActivity(intent);
    }

    public void btnDoctorSuggestion_Click(View view) {
        Intent intent = new Intent(this, WebviewActivity.class);
        startActivity(intent);
    }

}
