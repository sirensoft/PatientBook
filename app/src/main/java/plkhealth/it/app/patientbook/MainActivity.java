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

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.pixplicity.easyprefs.library.Prefs;
import com.readystatesoftware.viewbadger.BadgeView;


import com.loopj.android.http.*;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity {


    ProgressDialog progress;


    MyGlobals myGlobal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final View viewParent = findViewById(R.id.myCoordinatorLayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.apps);
        //toolbar.setTitle(getResources().getText(R.string.app_title));

        Prefs.putString("group", "risk");
        String group = Prefs.getString("group", "common");

        FirebaseMessaging.getInstance().subscribeToTopic(group);
        String firebase_token = FirebaseInstanceId.getInstance().getToken();
        Prefs.putString("firebase_token", firebase_token);
        Log.d("Tokend", "InstanceID token: " + firebase_token);


        //SharedPreferences.Editor editor = pref.edit();
        Prefs.putBoolean("patient_activated", true);
        // editor.putBoolean("patient_activated",true);
        //editor.apply();


        View btn_appointment = findViewById(R.id.btn_appointment);
        BadgeView badge1 = new BadgeView(this, btn_appointment);
        badge1.setText("N");
        badge1.show();

        View btn_promotion = findViewById(R.id.btn_promotion);
        BadgeView badge2 = new BadgeView(this, btn_promotion);
        badge2.setText("N");
        badge2.show();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean patient_activated = Prefs.getBoolean("patient_activated", false);
                if (!patient_activated) {
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
                                progress.dismiss();
                                String name = Prefs.getString("patient_cid", "");
                                Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).start();

            }
        });

        myGlobal = new MyGlobals(getApplicationContext());
        myGlobal.setBadge(getApplicationContext(), 0);


        // test
        AsyncHttpClient client = new AsyncHttpClient();
        String msg= "ข้อความจากมือถือ Asus";
        String url = "http://utehn.plkhealth.go.th/appapi/frontend/web/test/add?m="+msg;
        client.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK"
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });






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
